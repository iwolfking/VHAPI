package xyz.iwolfking.vhapi.api.registry.gen.palette;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.registry.gen.template_pools.TemplatePoolProcessors;

import java.util.*;
import java.util.function.Consumer;

public class PaletteProcessors {
    public interface TileProcessor {
        JsonElement toJson();
    }

    public static final class ReferenceProcessor implements TileProcessor {
        private final String id;

        public ReferenceProcessor(String id) { this.id = id; }

        @Override
        public JsonElement toJson() {
            JsonObject o = new JsonObject();
            o.addProperty("type", "reference");
            o.addProperty("id", id);
            return o;
        }
    }

    public static final class WeightedTargetProcessor implements TileProcessor {
        private final String target;
        private final Map<String, Integer> output;

        public WeightedTargetProcessor(String target, Map<String,Integer> output) {
            this.target = target;
            this.output = new LinkedHashMap<>(output);
        }

        @Override
        public JsonElement toJson() {
            JsonObject o = new JsonObject();
            o.addProperty("type", "weighted_target");
            o.addProperty("target", target);

            JsonObject out = new JsonObject();
            for (Map.Entry<String,Integer> e : output.entrySet()) {
                out.addProperty(e.getKey(), e.getValue());
            }
            o.add("output", out);
            return o;
        }
    }

    public static final class LeveledProcessor implements TileProcessor {
        private final Consumer<TemplatePoolProcessors.ProcessorConsumer> processors;

        public LeveledProcessor(Consumer<TemplatePoolProcessors.ProcessorConsumer>  processors) {
            this.processors = processors;
        }

        @Override
        public JsonElement toJson() {
            JsonObject o = new JsonObject();
            o.addProperty("type", "leveled");
            JsonArray levels = new JsonArray();

            TemplatePoolProcessors.ProcessorConsumer builder = new TemplatePoolProcessors.ProcessorConsumer();
            processors.accept(builder);

            for (TemplatePoolProcessors.TemplateProcessor processor : builder.getProcessors()) {
                levels.add(processor.toJson());
            }

            o.add("levels", levels);
            return o;
        }

    }


    public class LevelProcessor implements TileProcessor {

        protected final int level;

        private LevelProcessor(int level) {
            this.level = level;
        }

        @Override
        public JsonElement toJson() {
            JsonObject o = new JsonObject();
            o.addProperty("level", level);
            return o;
        }
    }

    public class SpawnerLevelProcessor extends LevelProcessor {
        private final String target;
        private final Consumer<SpawnerElement> elements;

        private SpawnerLevelProcessor(int level, String target, Consumer<SpawnerElement> elements) {
            super(level);
            this.target = target;
            this.elements = elements;
        }

        @Override
        public JsonElement toJson() {
            JsonObject o = (JsonObject)super.toJson();
            o.addProperty("type", "spawner");
            o.addProperty("target", target);

            JsonArray spawnerElements = new JsonArray();
            SpawnerElement builder = new SpawnerElement();
            elements.accept(builder);

            builder.getEntries().forEach((resourceLocation, integer) -> {
                JsonObject entry = new JsonObject();
                JsonObject element = new JsonObject();
                element.addProperty(resourceLocation.toString(), integer);
                entry.add("elements", element);
                entry.addProperty("weight", 1);
                spawnerElements.add(entry);
            });

            o.add("output", spawnerElements);

            return o;
        }

        private class SpawnerElement {
            private final Map<ResourceLocation, Integer> entries = new HashMap<>();

            public SpawnerElement add(ResourceLocation entity, Integer weight) {
                entries.put(entity, weight);
                return this;
            }

            public Map<ResourceLocation, Integer> getEntries() {
                return entries;
            }
        }
    }

    public static final class TemplateStackTileProcessor implements TileProcessor {
        private final String target;
        private final List<String> stack;

        public TemplateStackTileProcessor(String target, List<String> stack) {
            this.target = target;
            this.stack = List.copyOf(stack);
        }

        @Override
        public JsonElement toJson() {
            JsonObject o = new JsonObject();
            o.addProperty("type", "template_stack_tile");
            o.addProperty("target", target);

            JsonArray arr = new JsonArray();
            for (String s : stack) arr.add(s);
            o.add("stack", arr);
            return o;
        }
    }

    public static final class TemplateStackSpawnerProcessor implements TileProcessor {
        private final String target;
        private final List<String> stack;

        public TemplateStackSpawnerProcessor(String target, List<String> stack) {
            this.target = target;
            this.stack = List.copyOf(stack);
        }

        @Override
        public JsonElement toJson() {
            JsonObject o = new JsonObject();
            o.addProperty("type", "template_stack_spawner");
            o.addProperty("target", target);
            JsonArray arr = new JsonArray();
            for (String s : stack) arr.add(s);
            o.add("stack", arr);
            return o;
        }
    }
}
