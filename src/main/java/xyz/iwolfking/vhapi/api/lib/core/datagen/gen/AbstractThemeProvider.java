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

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public abstract class AbstractThemeProvider implements DataProvider {

    protected final DataGenerator generator;
    protected final String modid;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private final Map<ResourceLocation, ThemeDefinition> themes = new LinkedHashMap<>();

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

            //Generate Theme registry file
            Path keyPath = output.resolve(
                    "data/" + id.getNamespace() + "/vault_configs/themes/" + id.getPath() + ".json"
            );

            JsonObject keyFile = new JsonObject();
            JsonArray keys = new JsonArray();

            JsonObject item = new JsonObject();
            item.addProperty("id", id.toString());
            item.addProperty("name", formatReadableName(id));
            item.addProperty("color", entry.getValue().themeColor);
            item.addProperty("1.0", id.toString());

            keys.add(item);
            keyFile.add("keys", keys);

            DataProvider.save(GSON, cache, keyFile, keyPath);

            //Generate Theme augment file
            Path augmentPath = output.resolve(
                    "data/" + id.getNamespace() + "/vault_configs/augments/" + id.getPath() + ".json"
            );

            JsonObject augmentFile = new JsonObject();
            JsonObject augmentMap = new JsonObject();
            JsonObject augmentEntry = new JsonObject();
            augmentFile.addProperty("dropChance", 0.2);
            augmentEntry.addProperty("the_vault:augment{theme:\"" + id + "\"}", 1);
            augmentMap.add(id.toString(), augmentEntry);
            augmentFile.add("drops", augmentMap);

            DataProvider.save(GSON, cache, augmentFile, augmentPath);

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
            JsonObject voidCrucibleConfigFile = new JsonObject();
            JsonArray themes = new JsonArray();
            JsonArray allowedBlocks = new JsonArray();
            themes.add(id.toString());
            voidCrucibleConfigFile.add("themes", themes);
            voidCrucibleConfigFile.add("allowedBlocks", allowedBlocks);

            Path voidCruciblePath = output.resolve(
                    "data/" + id.getNamespace() + "/vault_configs/void_crucible/" + id.getPath() + ".json"
            );

            DataProvider.save(GSON, cache, voidCrucibleConfigFile, voidCruciblePath);

            JsonObject themeLoreFile = new JsonObject();

            //Generate Theme lore/add to Theme lore group
            if(def.themeGroup != null && def.themeLore == null) {
                themeLoreFile.add("series", new JsonObject());
                JsonObject augmentGroupEntry = new JsonObject();
                JsonArray augmentIds = new JsonArray();
                augmentIds.add(id.toString());
                augmentGroupEntry.add(def.themeGroup, augmentIds);
                themeLoreFile.add("augments", augmentGroupEntry);
            }

            if(def.themeGroup != null && def.themeLore != null) {
                JsonObject series = new JsonObject();
                JsonObject seriesEntry = new JsonObject();
                seriesEntry.addProperty("displayName", def.themeLore.displayName);
                seriesEntry.addProperty("colour", def.themeLore.colour);
                JsonArray description = new JsonArray();
                Arrays.stream(def.themeLore.description).forEach(descriptionData -> {
                    description.add(descriptionData.getDescription());
                });
                seriesEntry.add("description", description);
                series.add(def.themeGroup, seriesEntry);
                themeLoreFile.add("series", series);

                JsonObject augmentGroupEntry = new JsonObject();
                JsonArray augmentIds = new JsonArray();
                augmentIds.add(id.toString());
                augmentGroupEntry.add(def.themeGroup, augmentIds);
                themeLoreFile.add("augments", augmentGroupEntry);
            }

            if(def.themeLore != null || def.themeGroup != null) {
                Path augmentLorePath = output.resolve(
                        "data/" + id.getNamespace() + "/vault_configs/theme_lore/" + id.getPath() + ".json"
                );

                DataProvider.save(GSON, cache, themeLoreFile, augmentLorePath);
            }

        }
    }

    @Override
    public String getName() {
        return modid + " Theme Provider";
    }

    /** Example: vhapi:universal_undersea → "Universal Undersea" */
    private static String formatReadableName(ResourceLocation rl) {
        String path = rl.getPath().replace('/', '_');

        String[] parts = path.split("_");
        StringBuilder sb = new StringBuilder();

        for (String p : parts) {
            if (p.isEmpty()) continue;
            sb.append(Character.toUpperCase(p.charAt(0)))
                    .append(p.substring(1))
                    .append(" ");
        }

        return sb.toString().trim();
    }
}
