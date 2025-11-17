package xyz.iwolfking.vhapi.api.lib.core.datagen.gen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.registry.gen.themes.ThemeBuilder;
import xyz.iwolfking.vhapi.api.registry.gen.themes.ThemeDefinition;

import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractThemeProvider implements DataProvider {

    protected final DataGenerator generator;
    protected final String modid;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
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

            Path path = output.resolve(
                    "data/" + id.getNamespace() + "/vault_configs/gen/themes/" + id.getPath() + ".json"
            );

            DataProvider.save(GSON, cache, def.toJson(), path);

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
