package xyz.iwolfking.vhapi.api.registry.gen.palette;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.*;

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

    public static class LeveledProcessor implements TileProcessor {
        private final List<LevelProcessor> levels = new ArrayList<>();

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

    public static class LevelProcessor implements TileProcessor {
        private final JsonObject json = new JsonObject();

        public LevelProcessor weightedLevel(int level, String target, int weight, Map<String, Integer> elements) {
            json.addProperty("level", level);
            json.addProperty("type", "spawner");
            json.addProperty("target", target);

            JsonArray output = new JsonArray();
            JsonObject elementsObj = new JsonObject();
            elements.forEach(elementsObj::addProperty);

            JsonObject entry = new JsonObject();
            entry.add("elements", elementsObj);
            entry.addProperty("weight", weight);
            output.add(entry);

            json.add("output", output);
            return this;
        }

        public LevelProcessor level(int level, String target, Map<String, Integer> outputMap) {
            json.addProperty("level", level);
            json.addProperty("type", "weighted_target");
            json.addProperty("target", target);

            JsonObject output = new JsonObject();
            outputMap.forEach(output::addProperty);
            json.add("output", output);

            return this;
        }

        @Override
        public JsonElement toJson() {
            return json;
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
