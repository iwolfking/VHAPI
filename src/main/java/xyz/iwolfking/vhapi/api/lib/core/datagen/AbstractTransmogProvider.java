package xyz.iwolfking.vhapi.api.lib.core.datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.loaders.gear.transmog.lib.GsonHandheldModel;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractTransmogProvider implements DataProvider {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private final DataGenerator generator;
    private final String modid;

    private final Map<String, Map<String, List<GsonHandheldModel>>> files = new LinkedHashMap<>();

    public AbstractTransmogProvider(DataGenerator generator, String modid) {
        this.generator = generator;
        this.modid = modid;
    }

    public void add(String itemType, String fileName, Consumer<List<GsonHandheldModel>> consumer) {
        files.computeIfAbsent(itemType, k -> new LinkedHashMap<>());
        List<GsonHandheldModel> list = new ArrayList<>();
        consumer.accept(list);
        files.get(itemType).put(fileName, list);
    }

    @Override
    public void run(HashCache cache) throws IOException {
        files.clear();
        registerModels();

        for (var typeEntry : files.entrySet()) {
            String itemType = typeEntry.getKey();

            for (var fileEntry : typeEntry.getValue().entrySet()) {
                String fileName = fileEntry.getKey();
                List<GsonHandheldModel> entries = fileEntry.getValue();

                GearModelFile file = new GearModelFile(entries);
                JsonObject json = file.toJson();

                Path path = generator.getOutputFolder().resolve(
                        "data/" + modid + "/vault_configs/gear/handheld_models/" + itemType + "/" + fileName + ".json"
                );

                DataProvider.save(GSON, cache, json, path);
            }
        }
    }

    public record GearModelFile(List<GsonHandheldModel> models) {

        public JsonObject toJson() {
            JsonObject root = new JsonObject();
            JsonArray arr = new JsonArray();

            for (GsonHandheldModel e : models) {
                arr.add(e.toJson());
            }

            root.add("MODELS", arr);
            return root;
        }
    }

    @Override
    public String getName() {
        return modid + " Gear Model Provider";
    }

    protected abstract void registerModels();
}
