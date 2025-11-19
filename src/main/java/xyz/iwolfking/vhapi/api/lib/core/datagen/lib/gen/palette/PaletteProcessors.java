package xyz.iwolfking.vhapi.api.lib.core.datagen.lib.gen.palette;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import iskallia.vault.block.PlaceholderBlock;

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

    public static final class WeightedGenericProcessor implements TileProcessor {
        private final String target;
        private final Consumer<JsonArray> output;

        public WeightedGenericProcessor(String target, Consumer<JsonArray> output) {
            this.target = target;
            this.output = output;
        }

        @Override
        public JsonElement toJson() {
            JsonObject o = new JsonObject();
            o.addProperty("type", "weighted_target");
            o.addProperty("target", target);

            JsonArray out = new JsonArray();
            output.accept(out);
            o.add("output", out);
            return o;
        }
    }

    public static class LeveledProcessor implements TileProcessor {
        final List<LevelProcessor> levels = new ArrayList<>();

        public LeveledProcessor addLevel(LevelProcessor entry) {
            levels.add(entry);
            return this;
        }

        @Override
        public JsonObject toJson() {
            JsonObject obj = new JsonObject();
            obj.addProperty("type", "leveled");

            JsonArray arr = new JsonArray();
            for (LevelProcessor level : levels) {
                arr.add(level.toJson());
            }
            obj.add("levels", arr);

            return obj;
        }
    }

    public static class PlaceholderProcessor extends LeveledProcessor {

        private final PlaceholderBlock.Type target;

        public PlaceholderProcessor(PlaceholderBlock.Type placeholderType) {
            target = placeholderType;
        }

        @Override
        public JsonObject toJson() {
            JsonObject obj = new JsonObject();
            obj.addProperty("type", "placeholder");

            obj.addProperty("target", target.name());

            JsonArray arr = new JsonArray();
            for (LevelProcessor level : this.levels) {
                arr.add(level.toJson());
            }
            obj.add("levels", arr);

            return obj;
        }
    }

    public static class LevelProcessor implements TileProcessor {
        private final int level;

        public LevelProcessor(int level) {
            this.level = level;
        }

        public int getLevel() {
            return level;
        }

        @Override
        public JsonElement toJson() {
            JsonObject o = new JsonObject();
            o.addProperty("level", level);
            return o;
        }
    }

    public static class WeightedLevelProcessor extends LevelProcessor {
        private final String type;
        private final String target;
        private final int weight;
        private final Consumer<Map<String, Integer>> elements;

        public WeightedLevelProcessor(int level, String type, String target, int weight, Consumer<Map<String, Integer>> elements) {
            super(level);
            this.type = type;
            this.target = target;
            this.weight = weight;
            this.elements = elements;
        }

        @Override
        public JsonElement toJson() {
            JsonObject o = super.toJson().getAsJsonObject();
            o.addProperty("type", type);
            o.addProperty("target", target);

            JsonArray output = new JsonArray();
            JsonObject elementsObj = new JsonObject();
            Map<String, Integer> elementsMap = new HashMap<>();
            elements.accept(elementsMap);
            elementsMap.forEach(elementsObj::addProperty);

            JsonObject entry = new JsonObject();
            entry.add("elements", elementsObj);
            entry.addProperty("weight", weight);
            output.add(entry);

            o.add("output", output);

            return o;
        }
    }

    public static class WeightedTargetLevelProcessor extends LevelProcessor {
        private final String target;
        private final Consumer<Map<String, Integer>> outputConsumer;

        public WeightedTargetLevelProcessor(int level, String target, Consumer<Map<String, Integer>> outputConsumer) {
            super(level);
            this.target = target;
            this.outputConsumer = outputConsumer;
        }

        @Override
        public JsonElement toJson() {
            JsonObject o = super.toJson().getAsJsonObject();
            o.addProperty("type", "weighted_target");
            o.addProperty("target", target);

            JsonObject output = new JsonObject();
            Map<String, Integer> outputMap = new HashMap<>();
            outputConsumer.accept(outputMap);
            outputMap.forEach(output::addProperty);
            o.add("output", output);

            return o;
        }
    }

    public static class ProbabilityLevelProcessor extends LevelProcessor {
        private final double probability;
        private final Consumer<Map<String, Integer>> successConsumer;
        private final Consumer<Map<String, Integer>> failureConsumer;

        public ProbabilityLevelProcessor(int level, double probability, Consumer<Map<String, Integer>> successConsumer, Consumer<Map<String, Integer>> failureConsumer) {
            super(level);
            this.probability = probability;
            this.successConsumer = successConsumer;
            this.failureConsumer = failureConsumer;
        }

        @Override
        public JsonElement toJson() {
            JsonObject o = super.toJson().getAsJsonObject();
            o.addProperty("probability", probability);

            JsonObject successOutput = new JsonObject();
            Map<String, Integer> successMap = new HashMap<>();
            successConsumer.accept(successMap);
            successMap.forEach(successOutput::addProperty);
            o.add("success", successOutput);

            JsonObject failureOutput = new JsonObject();
            Map<String, Integer> failureMap = new HashMap<>();
            failureConsumer.accept(failureMap);
            failureMap.forEach(failureOutput::addProperty);
            o.add("failure", failureOutput);

            return o;
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
