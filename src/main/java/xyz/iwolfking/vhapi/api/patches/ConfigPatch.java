package xyz.iwolfking.vhapi.api.patches;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record ConfigPatch(@Nullable JsonElement create, List<Op> ops) {
    public static final String SET = "set";
    public static final String ADD = "add";
    public static final String REMOVE = "remove";
    public static final String APPEND = "append";
    public static final String SCALE = "scale";
    public static final String VALUE_SELECTOR = "$value";

    public record Op(String type, JsonArray path, @Nullable JsonElement value) {
    }

    public record Result(JsonElement json, List<String> failures) {
    }

    private static final class Failure extends RuntimeException {
        private Failure(String message) {
            super(message, null, false, false);
        }
    }

    public ConfigPatch {
        ops = List.copyOf(ops);
    }

    public static ConfigPatch fromJson(JsonObject json) {
        List<Op> ops = new ArrayList<>();
        if (json.has("ops")) {
            for (JsonElement element : json.getAsJsonArray("ops")) {
                JsonObject op = element.getAsJsonObject();
                ops.add(new Op(op.get("op").getAsString(), op.getAsJsonArray("path"), op.get("value")));
            }
        }
        return new ConfigPatch(json.get("create"), ops);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        if (this.create != null) {
            json.add("create", this.create);
        }
        JsonArray array = new JsonArray();
        for (Op op : this.ops) {
            JsonObject entry = new JsonObject();
            entry.addProperty("op", op.type());
            entry.add("path", op.path());
            if (op.value() != null) {
                entry.add("value", op.value());
            }
            array.add(entry);
        }
        json.add("ops", array);
        return json;
    }

    public Result apply(JsonElement base) {
        JsonElement root = base.deepCopy();
        List<String> failures = new ArrayList<>();
        for (int i = 0; i < this.ops.size(); i++) {
            Op op = this.ops.get(i);
            try {
                applyOp(root, op);
            } catch (Failure e) {
                failures.add("op " + i + " " + op.type() + " " + op.path() + ": " + e.getMessage());
            }
        }
        return new Result(root, failures);
    }

    private static void applyOp(JsonElement root, Op op) {
        JsonArray path = op.path();
        String type = op.type();
        JsonElement value = op.value();
        check(path.size() > 0, "empty path");
        check(REMOVE.equals(type) || value != null, type + " has no value");
        check(!SCALE.equals(type) || isNumber(value), "scale factor " + value + " is not a number");
        if (APPEND.equals(type)) {
            JsonElement target = walk(root, path, path.size());
            check(target.isJsonArray(), "target is not an array");
            target.getAsJsonArray().add(value.deepCopy());
            return;
        }
        JsonElement parent = walk(root, path, path.size() - 1);
        JsonElement last = path.get(path.size() - 1);
        if (parent.isJsonObject()) {
            check(isKey(last), "object step needs a key, got " + last);
            JsonObject object = parent.getAsJsonObject();
            String key = last.getAsString();
            switch (type) {
                case ADD -> {
                    check(!object.has(key), "key " + key + " already exists");
                    object.add(key, value.deepCopy());
                }
                case SET -> {
                    present(object, key);
                    object.add(key, value.deepCopy());
                }
                case REMOVE -> {
                    present(object, key);
                    object.remove(key);
                }
                case SCALE -> object.add(key, scaled(numberAt(present(object, key), "key " + key), value));
                default -> throw new Failure("unknown op");
            }
        } else if (parent.isJsonArray()) {
            JsonArray array = parent.getAsJsonArray();
            int index = index(array, last);
            switch (type) {
                case SET -> array.set(index, value.deepCopy());
                case REMOVE -> array.remove(index);
                case SCALE -> array.set(index, scaled(numberAt(array.get(index), "element " + index), value));
                default -> throw new Failure(type + " cannot target an array element");
            }
        } else {
            throw new Failure("parent is not an object or array");
        }
    }

    private static JsonElement walk(JsonElement root, JsonArray path, int steps) {
        JsonElement node = root;
        for (int i = 0; i < steps; i++) {
            JsonElement step = path.get(i);
            if (node.isJsonObject()) {
                check(isKey(step), "step " + i + " needs a key, got " + step);
                node = present(node.getAsJsonObject(), step.getAsString());
            } else if (node.isJsonArray()) {
                node = node.getAsJsonArray().get(index(node.getAsJsonArray(), step));
            } else {
                throw new Failure("step " + i + " walks into a value");
            }
        }
        return node;
    }

    private static JsonElement present(JsonObject object, String key) {
        JsonElement element = object.get(key);
        check(element != null, "key " + key + " is missing");
        return element;
    }

    private static int index(JsonArray array, JsonElement step) {
        if (step.isJsonPrimitive() && step.getAsJsonPrimitive().isNumber()) {
            int index = step.getAsInt();
            check(index >= 0 && index < array.size(), "index " + index + " is outside 0.." + (array.size() - 1));
            return index;
        }
        check(step.isJsonObject() && step.getAsJsonObject().size() == 1, "array step needs an index or a one-entry selector, got " + step);
        Map.Entry<String, JsonElement> selector = step.getAsJsonObject().entrySet().iterator().next();
        int found = -1;
        for (int i = 0; i < array.size(); i++) {
            JsonElement element = array.get(i);
            boolean matches = VALUE_SELECTOR.equals(selector.getKey())
                    ? element.equals(selector.getValue())
                    : element.isJsonObject() && selector.getValue().equals(element.getAsJsonObject().get(selector.getKey()));
            if (matches) {
                check(found < 0, "selector " + step + " matches more than one element");
                found = i;
            }
        }
        check(found >= 0, "selector " + step + " matches nothing");
        return found;
    }

    private static JsonElement numberAt(JsonElement element, String where) {
        check(isNumber(element), where + " holds " + element + ", not a number");
        return element;
    }

    private static void check(boolean condition, String problem) {
        if (!condition) {
            throw new Failure(problem);
        }
    }

    private static boolean isKey(JsonElement step) {
        return step.isJsonPrimitive() && step.getAsJsonPrimitive().isString();
    }

    private static boolean isNumber(JsonElement element) {
        return element != null && element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber();
    }

    public static JsonPrimitive scaled(JsonElement number, JsonElement factor) {
        BigDecimal product = number.getAsBigDecimal().multiply(factor.getAsBigDecimal()).stripTrailingZeros();
        return new JsonPrimitive(product.scale() < 0 ? product.setScale(0) : product);
    }
}
