package xyz.iwolfking.vhapi.api.datagen.lib.gen.template_pools;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class TemplatePoolProcessors {
    public interface Processor {
        JsonElement toJson();
    }

    public static final class ReferenceProcessor implements Processor {
        private final String id;
        private final int weight;

        public ReferenceProcessor(String id, int weight) {
            this.id = id;
            this.weight = weight;
        }

        @Override
        public JsonElement toJson() {
            JsonObject o = new JsonObject();
            o.addProperty("reference", id);
            o.addProperty("weight", weight);
            return o;
        }
    }

    public static final class TemplateProcessor implements Processor {
        private final String id;
        private final int weight;
        private final Consumer<PaletteConsumer> paletteConsumer;

        public TemplateProcessor(String id, int weight, Consumer<PaletteConsumer> paletteConsumer) {
            this.id = id;
            this.weight = weight;
            this.paletteConsumer = paletteConsumer;
        }

        @Override
        public JsonElement toJson() {
            JsonObject o = new JsonObject();

            JsonObject value = new JsonObject();

            value.addProperty("template", id);

            JsonArray paletteArray = new JsonArray();
            PaletteConsumer builder = new PaletteConsumer();
            paletteConsumer.accept(builder);

            for (ResourceLocation palette : builder.getPalettes()) {
                paletteArray.add(palette.toString());
            }

            value.add("palettes", paletteArray);

            o.addProperty("weight", weight);
            o.add("value", value);

            return o;
        }
    }

    public static final class PoolProcessor implements Processor {
        private final int weight;
        private final Consumer<ProcessorConsumer> processorConsumer;

        public PoolProcessor(int weight, Consumer<ProcessorConsumer> paletteConsumer) {
            this.weight = weight;
            this.processorConsumer = paletteConsumer;
        }

        @Override
        public JsonElement toJson() {
            JsonObject o = new JsonObject();

            JsonObject value = new JsonObject();

            o.addProperty("weight", weight);

            JsonArray processorArray = new JsonArray();
            ProcessorConsumer builder = new ProcessorConsumer();
            processorConsumer.accept(builder);

            for (TemplateProcessor processor : builder.getProcessors()) {
                processorArray.add(processor.toJson());
            }

            value.add("pool", processorArray);

            return o;
        }
    }


    public static class PaletteConsumer {
        private final List<ResourceLocation> palettes = new ArrayList<>();

        public PaletteConsumer add(ResourceLocation palette) {
            palettes.add(palette);
            return this;
        }

        public List<ResourceLocation> getPalettes() {
            return palettes;
        }
    }

    public static class ProcessorConsumer {
        private final List<TemplateProcessor> processors = new ArrayList<>();

        public ProcessorConsumer add(TemplateProcessor procesor) {
            processors.add(procesor);
            return this;
        }

        public List<TemplateProcessor> getProcessors() {
            return processors;
        }
    }
}
