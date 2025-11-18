package xyz.iwolfking.vhapi.api.lib.core.datagen.gen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.registry.gen.palette.PaletteBuilder;
import xyz.iwolfking.vhapi.api.registry.gen.palette.PaletteDefinition;
import xyz.iwolfking.vhapi.api.registry.gen.palette.PaletteProcessors;

import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractPaletteProvider implements DataProvider {

    protected final DataGenerator generator;
    protected final String modid;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private final Map<ResourceLocation, PaletteDefinition> palettes = new LinkedHashMap<>();

    public AbstractPaletteProvider(DataGenerator generator, String modid) {
        this.generator = generator;
        this.modid = modid;
    }

    protected abstract void registerPalettes();

    protected void add(ResourceLocation id, Consumer<PaletteBuilder> consumer) {
        PaletteBuilder builder = new PaletteBuilder();
        consumer.accept(builder);
        palettes.put(id, builder.build());
    }


    @Override
    public void run(HashCache cache) throws IOException {
        palettes.clear();
        registerPalettes();

        Path output = generator.getOutputFolder();

        for (var entry : palettes.entrySet()) {
            ResourceLocation id = entry.getKey();
            PaletteDefinition def = entry.getValue();

            Path palettePath = output.resolve(
                    "data/" + id.getNamespace() + "/vault_configs/gen/palettes/" + id.getPath() + ".json"
            );
            DataProvider.save(GSON, cache, def.toJson(), palettePath);

            Path keyPath = output.resolve(
                    "data/" + id.getNamespace() + "/vault_configs/palettes/" + id.getPath() + ".json"
            );

            JsonObject keyFile = new JsonObject();
            JsonArray keys = new JsonArray();

            JsonObject item = new JsonObject();
            item.addProperty("id", id.toString());
            item.addProperty("name", formatReadableName(id));
            item.addProperty("1.0", id.toString());

            keys.add(item);
            keyFile.add("keys", keys);

            DataProvider.save(GSON, cache, keyFile, keyPath);
        }
    }

    @Override
    public String getName() {
        return modid + " Palette Provider";
    }

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
