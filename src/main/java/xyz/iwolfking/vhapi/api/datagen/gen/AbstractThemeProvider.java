package xyz.iwolfking.vhapi.api.datagen.gen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.datagen.lib.gen.themes.ThemeBuilder;
import xyz.iwolfking.vhapi.api.datagen.lib.gen.themes.ThemeDefinition;
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
    private final Map<ResourceLocation, JsonObject> augmentEntries = new LinkedHashMap<>();
    private final Map<String, List<String>> themeGroupEntries = new LinkedHashMap<>();
    private final Map<String, JsonObject> themeLoreEntries = new LinkedHashMap<>();

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

            Path path = output.resolve(
                    "data/" + id.getNamespace() + "/vault_configs/gen/themes/" + id.getPath() + ".json"
            );
            DataProvider.save(GSON, cache, def.toJson(), path);

            JsonObject item = new JsonObject();
            item.addProperty("id", id.toString());
            item.addProperty("name", ResourceLocUtils.formatReadableName(id));
            item.addProperty("color", def.themeColor);
            item.addProperty("1.0", id.toString());
            themeRegistryEntries.add(item);

            JsonObject augmentEntry = new JsonObject();
            String augmentStackKey = def.themeGroup != null
                    ? "the_vault:augment{series:\"" + def.themeGroup + "\"}"
                    : "the_vault:augment{theme:\"" + id + "\"}";
            augmentEntry.addProperty(augmentStackKey, 1);
            augmentEntries.put(id, augmentEntry);

            if (!def.levelEntries.isEmpty()) {
                JsonObject root = new JsonObject();
                JsonObject themesObject = new JsonObject();

                for (Map.Entry<String, Set<Integer>> levelEntry : def.levelEntries.entrySet()) {
                    String themeId = levelEntry.getKey();
                    JsonArray themeLevels = new JsonArray();

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

            voidCrucibleEntries.add(id.toString());

            if (def.themeGroup != null) {
                themeGroupEntries.computeIfAbsent(def.themeGroup, k -> new ArrayList<>()).add(id.toString());

                if (def.themeLore != null && !themeLoreEntries.containsKey(def.themeGroup)) {
                    JsonObject seriesEntry = new JsonObject();
                    seriesEntry.addProperty("displayName", def.themeLore.displayName);

                    JsonArray description = new JsonArray();
                    if (def.themeLore.description != null) {
                        Arrays.stream(def.themeLore.description).forEach(descriptionData ->
                                description.add(descriptionData.getDescription())
                        );
                    }
                    seriesEntry.add("description", description);
                    seriesEntry.addProperty("colour", def.themeLore.colour);

                    if (def.atmosphere != null) {
                        seriesEntry.addProperty("atmosphere", def.atmosphere.toString());
                    }
                    seriesEntry.addProperty("minLevel", def.minLevel);
                    seriesEntry.addProperty("seasonal", def.seasonal);
                    if (def.perk != null) {
                        seriesEntry.addProperty("perk", def.perk);
                    }

                    if (def.flair != null) {
                        JsonObject flairObj = new JsonObject();
                        if (def.flair.floor() != null) {
                            flairObj.addProperty("floor", def.flair.floor().toString());
                        }
                        if (def.flair.wall() != null) {
                            flairObj.addProperty("wall", def.flair.wall().toString());
                        }
                        if (def.flair.accent() != null) {
                            flairObj.addProperty("accent", def.flair.accent().toString());
                        }
                        if (def.flair.particle() != null) {
                            flairObj.addProperty("particle", def.flair.particle().toString());
                        }
                        seriesEntry.add("flair", flairObj);
                    }

                    if (def.mobPalettes != null && !def.mobPalettes.isEmpty()) {
                        JsonArray mobPalettes = new JsonArray();
                        def.mobPalettes.forEach(loc -> mobPalettes.add(loc.toString()));
                        seriesEntry.add("mobPalettes", mobPalettes);
                    }

                    themeLoreEntries.put(def.themeGroup, seriesEntry);
                }
            }
        }

        Path keyPath = output.resolve("data/" + modid + "/vault_configs/themes/themes.json");
        JsonObject keyFile = new JsonObject();
        JsonArray keys = new JsonArray();
        themeRegistryEntries.forEach(keys::add);
        keyFile.add("keys", keys);
        DataProvider.save(GSON, cache, keyFile, keyPath);

        Path augmentPath = output.resolve("data/" + modid + "/vault_configs/augments/augments.json");
        JsonObject augmentFile = new JsonObject();
        JsonObject augmentMap = new JsonObject();
        augmentFile.addProperty("dropChance", 0.2);
        augmentFile.addProperty("pieceDropChance", 0.5);

        augmentEntries.forEach((resourceLocation, jsonObject) ->
                augmentMap.add(resourceLocation.toString(), jsonObject)
        );
        augmentFile.add("drops", augmentMap);
        DataProvider.save(GSON, cache, augmentFile, augmentPath);

        Path voidCruciblePath = output.resolve("data/" + modid + "/vault_configs/void_crucible/themes_crucible_config.json");
        JsonObject voidCrucibleConfigFile = new JsonObject();
        JsonArray themesArray = new JsonArray();
        voidCrucibleEntries.forEach(themesArray::add);
        voidCrucibleConfigFile.add("themes", themesArray);
        voidCrucibleConfigFile.add("allowedBlocks", new JsonArray());
        DataProvider.save(GSON, cache, voidCrucibleConfigFile, voidCruciblePath);

        Path augmentLorePath = output.resolve("data/" + modid + "/vault_configs/theme_lore/theme_lore.json");
        JsonObject themeLoreFile = new JsonObject();

        JsonObject seriesMap = new JsonObject();
        themeLoreEntries.forEach(seriesMap::add);
        themeLoreFile.add("series", seriesMap);

        JsonObject augmentsMap = new JsonObject();
        themeGroupEntries.forEach((group, ids) -> {
            JsonArray idsArray = new JsonArray();
            ids.forEach(idsArray::add);
            augmentsMap.add(group, idsArray);
        });
        themeLoreFile.add("augments", augmentsMap);
        themeLoreFile.add("retiredThemes", new JsonObject());
        themeLoreFile.addProperty("spawnersPerCommonRoom", 10.06);

        DataProvider.save(GSON, cache, themeLoreFile, augmentLorePath);
    }

    @Override
    public String getName() {
        return modid + " Theme Provider";
    }
}