package xyz.iwolfking.vhapi.api.lib.core.datagen.gen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.lib.core.datagen.lib.gen.themes.ThemeBuilder;
import xyz.iwolfking.vhapi.api.lib.core.datagen.lib.gen.themes.ThemeDefinition;
import xyz.iwolfking.vhapi.api.util.ResourceLocUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;

public abstract class AbstractThemeProvider implements DataProvider {

    protected final DataGenerator generator;
    protected final String modid;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private final Map<ResourceLocation, ThemeDefinition> themes = new LinkedHashMap<>();

    private final List<JsonObject> themeRegistryEntries = new ArrayList<>();
    private final List<String> voidCrucibleEntries = new ArrayList<>();
    private final Map<ResourceLocation, JsonObject> augmentEntries = new HashMap<>();
    private final Map<String, List<String>> themeGroupEntries = new HashMap<>();
    private final Map<String, JsonObject> themeLoreEntries = new HashMap<>();

    public AbstractThemeProvider(DataGenerator generator, String modid) {
        this.generator = generator;
        this.modid = modid;
    }

    protected abstract void registerThemes();

    protected void add(ResourceLocation id, Consumer<ThemeBuilder> consumer) {
        ThemeBuilder builder = new ThemeBuilder();
        consumer.accept(builder);
        themes.put(id, builder.build());
    }

    @Override
    public void run(HashCache cache) throws IOException {
        themes.clear();
        registerThemes();

        Path output = generator.getOutputFolder();

        for (var entry : themes.entrySet()) {
            ResourceLocation id = entry.getKey();
            ThemeDefinition def = entry.getValue();

            //Generate Theme gen file
            Path path = output.resolve(
                    "data/" + id.getNamespace() + "/vault_configs/gen/themes/" + id.getPath() + ".json"
            );

            DataProvider.save(GSON, cache, def.toJson(), path);

            JsonObject item = new JsonObject();
            item.addProperty("id", id.toString());
            item.addProperty("name", ResourceLocUtils.formatReadableName(id));
            item.addProperty("color", entry.getValue().themeColor);
            item.addProperty("1.0", id.toString());

            themeRegistryEntries.add(item);

            //Generate Theme augment file
            JsonObject augmentEntry = new JsonObject();

            augmentEntry.addProperty("the_vault:augment{theme:\"" + id + "\"}", 1);

            augmentEntries.put(id, augmentEntry);

            //Generate Theme Pool file (Vault Crystal Config)
            if (!def.levelEntries.isEmpty()) {

                JsonObject root = new JsonObject();
                JsonObject themesObject = new JsonObject();
                JsonArray themeLevels = new JsonArray();

                for (Map.Entry<String, Set<Integer>> levelEntry : def.levelEntries.entrySet()) {
                    String themeId = levelEntry.getKey();

                    for (int level : levelEntry.getValue()) {

                        JsonObject levelObject = new JsonObject();
                        levelObject.addProperty("level", level);

                        JsonArray pool = new JsonArray();
                        JsonObject poolEntry = new JsonObject();
                        poolEntry.addProperty("value", id.toString());
                        poolEntry.addProperty("weight", def.themeWeight);
                        pool.add(poolEntry);

                        levelObject.add("pool", pool);
                        themeLevels.add(levelObject);
                    }

                    themesObject.add(themeId, themeLevels);
                }

                root.add("THEMES", themesObject);

                Path poolPath = output.resolve(
                        "data/" + id.getNamespace() + "/vault_configs/vault/crystal/" + id.getPath() + ".json"
                );

                DataProvider.save(GSON, cache, root, poolPath);
            }

            //Generate Void Crucible config
            voidCrucibleEntries.add(id.toString());


            //Generate Theme lore/add to Theme lore group
            if(def.themeGroup != null && def.themeLore == null) {
                if(themeGroupEntries.containsKey(def.themeGroup)) {
                    themeGroupEntries.get(def.themeGroup).add(id.toString());
                }
                else {
                    List<String> ids = new ArrayList<>();
                    ids.add(id.toString());
                    themeGroupEntries.put(def.themeGroup, ids);
                }
            }

            if(def.themeGroup != null && def.themeLore != null) {
                JsonObject seriesEntry = new JsonObject();

                seriesEntry.addProperty("displayName", def.themeLore.displayName);
                seriesEntry.addProperty("colour", def.themeLore.colour);
                JsonArray description = new JsonArray();
                Arrays.stream(def.themeLore.description).forEach(descriptionData -> {
                    description.add(descriptionData.getDescription());
                });
                seriesEntry.add("description", description);
                themeLoreEntries.put(def.themeGroup, seriesEntry);

                if(themeGroupEntries.containsKey(def.themeGroup)) {
                    themeGroupEntries.get(def.themeGroup).add(id.toString());
                }
                else {
                    List<String> ids = new ArrayList<>();
                    ids.add(id.toString());
                    themeGroupEntries.put(def.themeGroup, ids);
                }
            }

        }

        //Generate Theme registry file
        Path keyPath = generator.getOutputFolder().resolve(
                "data/" + modid + "/vault_configs/themes/themes.json"
        );

        JsonObject keyFile = new JsonObject();
        JsonArray keys = new JsonArray();

        for(JsonObject entry : themeRegistryEntries) {
            keys.add(entry);
        }

        keyFile.add("keys", keys);

        DataProvider.save(GSON, cache, keyFile, keyPath);

        //Generate Theme augment file
        Path augmentPath = output.resolve(
                "data/" + modid + "/vault_configs/augments/augments.json"
        );


        JsonObject augmentFile = new JsonObject();
        JsonObject augmentMap = new JsonObject();
        augmentFile.addProperty("dropChance", 0.2);
        augmentEntries.forEach(((resourceLocation, jsonObject) -> {
            augmentMap.add(resourceLocation.toString(), jsonObject);
        }));

        augmentFile.add("drops", augmentMap);

        DataProvider.save(GSON, cache, augmentFile, augmentPath);


        //Void Crucible file
        JsonObject voidCrucibleConfigFile = new JsonObject();
        JsonArray themes = new JsonArray();
        JsonArray allowedBlocks = new JsonArray();
        voidCrucibleEntries.forEach(themes::add);
        voidCrucibleConfigFile.add("themes", themes);
        voidCrucibleConfigFile.add("allowedBlocks", allowedBlocks);

        Path voidCruciblePath = output.resolve(
                "data/" + modid + "/vault_configs/void_crucible/themes_crucible_config.json"
        );

        DataProvider.save(GSON, cache, voidCrucibleConfigFile, voidCruciblePath);

        //Combined Theme Lore file
        //Augments Map
        JsonObject themeLoreFile = new JsonObject();
        themeLoreFile.add("series", new JsonObject());
        JsonObject augmentGroupEntry = new JsonObject();
        themeGroupEntries.forEach((s, strings) -> {
            JsonArray augmentIds = new JsonArray();
            for(String id : strings) {
                augmentIds.add(id);
            }
            augmentGroupEntry.add(s, augmentIds);
        });
        themeLoreFile.add("augments", augmentGroupEntry);

        //Series Map
        JsonObject series = new JsonObject();
        themeLoreEntries.forEach(series::add);
        themeLoreFile.add("series", series);

        Path augmentLorePath = output.resolve(
                "data/" + modid + "/vault_configs/theme_lore/theme_lore.json"
        );

        DataProvider.save(GSON, cache, themeLoreFile, augmentLorePath);

    }

    @Override
    public String getName() {
        return modid + " Theme Provider";
    }
}
