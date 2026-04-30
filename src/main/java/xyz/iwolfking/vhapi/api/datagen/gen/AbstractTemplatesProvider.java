package xyz.iwolfking.vhapi.api.datagen.gen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.datagen.lib.BasicListBuilder;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractTemplatesProvider implements DataProvider {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private final DataGenerator generator;
    private final String modid;
    private final List<Builder> builders = new ArrayList<>();

    private final List<JsonObject> templatePoolRegistryEntries = new ArrayList<>();

    public AbstractTemplatesProvider(DataGenerator generator, String modid) {
        this.generator = generator;
        this.modid = modid;
    }

    protected abstract void registerTemplates();


    protected void add(String filename, Consumer<Builder> consumer) {
        Builder builder = new Builder(filename);
        consumer.accept(builder);
        builders.add(builder);
    }

    public static class Builder extends BasicListBuilder<StructureTemplate> {

        private final String name;

        public Builder(String name) {this.name = name;}

        public Builder addStructureTemplate(ResourceLocation id, String name, ResourceLocation structureID) {
            this.add(new StructureTemplate(id, name, structureID.toString()));
            return this;
        }

        public Builder addStructureTemplate(ResourceLocation id, String name, String structureID) {
            this.add(new StructureTemplate(id, name, structureID));
            return this;
        }

    }

    @Override
    public void run(HashCache cache) throws IOException {
        registerTemplates();

        for (Builder builder : builders) {

            for (var templateBuilder : builder.build()) {
                JsonObject item = new JsonObject();
                item.addProperty("id", templateBuilder.id().toString());
                item.addProperty("name", templateBuilder.name());

                JsonObject structureInfo = new JsonObject();
                structureInfo.addProperty("type", "structure");
                structureInfo.addProperty("path", templateBuilder.structureID());
                item.add("1.0", structureInfo);

                templatePoolRegistryEntries.add(item);
            }
            Path keyPath = generator.getOutputFolder().resolve(
                "data/" + modid + "/vault_configs/templates/" + builder.name + ".json"
            );
            JsonObject keyFile = new JsonObject();
            JsonArray keys = new JsonArray();

            for (JsonObject entry : templatePoolRegistryEntries) {
                keys.add(entry);
            }

            keyFile.add("keys", keys);

            DataProvider.save(GSON, cache, keyFile, keyPath);
        }
    }

    @Override
    public String getName() {
        return modid + " Template Pool Provider";
    }


    public record StructureTemplate(ResourceLocation id, String name, String structureID){}
}
