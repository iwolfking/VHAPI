package xyz.iwolfking.vhapi.api.datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import xyz.iwolfking.vhapi.api.patches.ConfigPatch;
import xyz.iwolfking.vhapi.api.patches.ConfigPatches;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractConfigPatchProvider implements DataProvider {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().serializeNulls().create();
    private static final String SEEDED_CONFIG_ROOT = "config/the_vault/";
    private final DataGenerator generator;
    private final String modId;
    private final Map<String, Patch> patches = new LinkedHashMap<>();
    private Map<String, ConfigPatch> built;

    protected AbstractConfigPatchProvider(DataGenerator generator, String modId) {
        this.generator = generator;
        this.modId = modId;
    }

    protected abstract void registerPatches();

    public final Map<String, ConfigPatch> patches() {
        if (this.built == null) {
            this.registerPatches();
            Map<String, ConfigPatch> out = new LinkedHashMap<>();
            this.patches.forEach((path, patch) -> out.put(path, patch.build()));
            this.built = Collections.unmodifiableMap(out);
        }
        return this.built;
    }

    protected final Patch patch(String path) {
        return this.patches.computeIfAbsent(path, p -> new Patch());
    }

    @Override
    public String getName() {
        return this.modId + " config patches";
    }

    @Override
    public void run(HashCache cache) throws IOException {
        for (Map.Entry<String, ConfigPatch> entry : this.patches().entrySet()) {
            String path = entry.getKey();
            JsonObject written = JsonParser.parseString(GSON.toJson(entry.getValue().toJson())).getAsJsonObject();
            ConfigPatch patch = ConfigPatch.fromJson(written);
            String unhooked = unhookedReason(path, patch);
            if (unhooked != null) {
                throw new IllegalStateException("Config patch for " + path + " would never apply: " + unhooked);
            }
            File seeded = new File(SEEDED_CONFIG_ROOT + path);
            JsonElement base;
            if (patch.create() != null) {
                if (seeded.exists()) {
                    throw new IllegalStateException("Config patch for " + path + " creates a file the seeded pack already ships");
                }
                base = patch.create();
            } else {
                if (!seeded.exists()) {
                    throw new IllegalStateException("Config patch for " + path + " targets a file the seeded pack does not ship");
                }
                try (Reader reader = new FileReader(seeded)) {
                    base = JsonParser.parseReader(reader);
                }
            }
            List<String> failures = patch.apply(base).failures();
            if (!failures.isEmpty()) {
                throw new IllegalStateException("Config patch for " + path + " does not apply to the seeded pack:\n  " + String.join("\n  ", failures));
            }
            DataProvider.save(GSON, cache, written, this.generator.getOutputFolder().resolve("data/" + this.modId + "/vault_configs/" + ConfigPatches.DIRECTORY + "/" + path));
        }
    }

    private static String unhookedReason(String path, ConfigPatch patch) {
        if (path.startsWith("lang/")) {
            return "locale variants are read without the patch hook";
        }
        boolean lootTable = path.startsWith("gen/1.0/loot_tables/");
        boolean crucibleItem = path.startsWith("void_crucible/");
        if (path.startsWith("gen/") && !lootTable) {
            return "only generation loot tables are hooked under gen/";
        }
        if (patch.create() != null && (lootTable || crucibleItem)) {
            return "create works only for files read through Config.readConfig";
        }
        return null;
    }

    public static JsonElement seeded(String path) {
        File file = new File(SEEDED_CONFIG_ROOT + path);
        if (!file.exists()) {
            throw new IllegalStateException("Config patches read " + path + ", which the seeded pack does not ship");
        }
        try (Reader reader = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {
            return JsonParser.parseReader(reader);
        } catch (IOException e) {
            throw new IllegalStateException("Config patches could not read the seeded " + path, e);
        }
    }

    public static List<String> seededFiles(String folder) {
        File[] files = new File(SEEDED_CONFIG_ROOT + folder).listFiles((dir, name) -> name.endsWith(".json"));
        if (files == null) {
            throw new IllegalStateException("Config patches read the folder " + folder + ", which the seeded pack does not ship");
        }
        List<String> paths = new ArrayList<>();
        for (File file : files) {
            paths.add(folder + file.getName());
        }
        Collections.sort(paths);
        return paths;
    }

    public static JsonObject id(String id) {
        return where("id", id);
    }

    public static JsonObject where(String field, Object value) {
        JsonObject selector = new JsonObject();
        selector.add(field, json(value));
        return selector;
    }

    public static JsonObject value(Object value) {
        return where(ConfigPatch.VALUE_SELECTOR, value);
    }

    public static JsonElement json(Object value) {
        if (value == null) {
            return JsonNull.INSTANCE;
        }
        if (value instanceof JsonElement element) {
            return element;
        }
        if (value instanceof Number number) {
            return new JsonPrimitive(number);
        }
        if (value instanceof Boolean bool) {
            return new JsonPrimitive(bool);
        }
        if (value instanceof String string) {
            return new JsonPrimitive(string);
        }
        throw new IllegalArgumentException("Not a JSON value: " + value);
    }

    public static JsonElement parse(String text) {
        return JsonParser.parseString(text);
    }

    public static final class Patch {
        private JsonElement create;
        private final List<ConfigPatch.Op> ops = new ArrayList<>();

        public Patch create(JsonElement content) {
            this.create = content;
            return this;
        }

        public Node at(Object... path) {
            return new Node(this, steps(new JsonArray(), path));
        }

        private void op(String type, JsonArray path, JsonElement value) {
            this.ops.add(new ConfigPatch.Op(type, path, value));
        }

        private ConfigPatch build() {
            return new ConfigPatch(this.create, this.ops);
        }
    }

    public static final class Node {
        private final Patch patch;
        private final JsonArray path;

        private Node(Patch patch, JsonArray path) {
            this.patch = patch;
            this.path = path;
        }

        public Node at(Object... more) {
            return new Node(this.patch, steps(this.path.deepCopy(), more));
        }

        public Node set(Object step, Object value) {
            this.patch.op(ConfigPatch.SET, steps(this.path.deepCopy(), step), json(value));
            return this;
        }

        public Node add(String key, Object value) {
            this.patch.op(ConfigPatch.ADD, steps(this.path.deepCopy(), key), json(value));
            return this;
        }

        public Node scale(Object step, Number factor) {
            this.patch.op(ConfigPatch.SCALE, steps(this.path.deepCopy(), step), json(factor));
            return this;
        }

        public Node remove(Object step) {
            this.patch.op(ConfigPatch.REMOVE, steps(this.path.deepCopy(), step), null);
            return this;
        }

        public Node append(Object value) {
            this.patch.op(ConfigPatch.APPEND, this.path.deepCopy(), json(value));
            return this;
        }
    }

    private static JsonArray steps(JsonArray path, Object... steps) {
        for (Object step : steps) {
            if (step instanceof Integer || step instanceof String || step instanceof JsonObject) {
                path.add(json(step));
            } else {
                throw new IllegalArgumentException("A patch path step is a key, an index or a selector, got " + step);
            }
        }
        return path;
    }
}
