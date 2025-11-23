package xyz.iwolfking.vhapi.api.datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractInscriptionProvider implements DataProvider {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    protected final DataGenerator generator;
    protected final String modId;

    private final Map<String, List<JsonObject>> pools = new LinkedHashMap<>();

    private final JsonObject poolToModel = new JsonObject();
    private JsonArray ringWeights = new JsonArray();

    public AbstractInscriptionProvider(DataGenerator generator, String modId) {
        this.generator = generator;
        this.modId = modId;
    }

    protected abstract void build();


    protected PoolBuilder addPool(String name) {
        List<JsonObject> list = pools.computeIfAbsent(name, k -> new ArrayList<>());
        return new PoolBuilder(list);
    }

    protected PoolEntryBuilder newPoolEntry(String poolName) {
        return new PoolEntryBuilder(poolName);
    }


    public static class PoolBuilder {
        private final List<JsonObject> target;

        public PoolBuilder(List<JsonObject> target) {
            this.target = target;
        }

        public LevelBuilder level(int lvl) {
            JsonObject obj = new JsonObject();
            obj.addProperty("level", lvl);
            JsonArray poolArray = new JsonArray();
            obj.add("pool", poolArray);
            target.add(obj);
            return new LevelBuilder(poolArray);
        }
    }

    public static class LevelBuilder {
        private final JsonArray poolArray;

        public LevelBuilder(JsonArray poolArray) {
            this.poolArray = poolArray;
        }

        public LevelBuilder entry(PoolEntryBuilder entry) {
            poolArray.add(entry.build());
            return this;
        }
    }

    public static class PoolEntryBuilder {
        private final JsonObject value = new JsonObject();
        private final JsonArray entries = new JsonArray();
        private int weight = 1;

        public PoolEntryBuilder(String poolName) {
            JsonObject entry = new JsonObject();
            entry.addProperty("pool", poolName);
            entries.add(entry);
        }

        public PoolEntryBuilder count(int count) {
            ((JsonObject) entries.get(0)).addProperty("count", count);
            return this;
        }

        public PoolEntryBuilder color(int color) {
            ((JsonObject) entries.get(0)).addProperty("color", color);
            return this;
        }

        public PoolEntryBuilder size(int min, int max) {
            JsonObject size = new JsonObject();
            size.addProperty("type", "uniform");
            size.addProperty("min", min);
            size.addProperty("max", max);
            value.add("size", size);
            return this;
        }

        public PoolEntryBuilder model(int min, int max) {
            JsonObject model = new JsonObject();
            model.addProperty("type", "uniform");
            model.addProperty("min", min);
            model.addProperty("max", max);
            value.add("model", model);
            return this;
        }

        public PoolEntryBuilder weight(int weight) {
            this.weight = weight;
            return this;
        }

        public JsonObject build() {
            value.add("entries", entries);
            JsonObject wrapper = new JsonObject();
            wrapper.add("value", value);
            wrapper.addProperty("weight", weight);
            return wrapper;
        }
    }


    protected void mapModel(String pool, int model) {
        poolToModel.addProperty(pool, model);
    }

    protected void setRingWeights(int... weights) {
        ringWeights = new JsonArray();
        for (int w : weights) ringWeights.add(w);
    }

    @Override
    public void run(HashCache cache) throws IOException {
        build();

        JsonObject root = new JsonObject();

        // pools
        JsonObject poolsObj = new JsonObject();
        for (var entry : pools.entrySet()) {
            JsonArray arr = new JsonArray();
            entry.getValue().forEach(arr::add);
            poolsObj.add(entry.getKey(), arr);
        }
        root.add("pools", poolsObj);

        root.add("poolToModel", poolToModel);

        root.add("ringWeights", ringWeights);

        Path target = generator.getOutputFolder()
                .resolve("data/" + modId + "/vault_configs/inscriptions/pools.json");

        DataProvider.save(GSON, cache, root, target);
    }

    @Override
    public String getName() {
        return modId + " Vault Inscription Pools";
    }
}
