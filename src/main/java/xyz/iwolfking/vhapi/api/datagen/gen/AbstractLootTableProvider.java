package xyz.iwolfking.vhapi.api.datagen.gen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import iskallia.vault.config.Config;
import iskallia.vault.core.data.key.LootTableKey;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import xyz.iwolfking.vhapi.api.datagen.lib.gen.palette.PaletteDefinition;
import xyz.iwolfking.vhapi.api.util.ResourceLocUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;

public abstract class AbstractLootTableProvider implements DataProvider {

    private final DataGenerator generator;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private final String modid;

    private final Map<ResourceLocation, LootTableFile> lootTables = new LinkedHashMap<>();

    public AbstractLootTableProvider(DataGenerator gen, String modid) {
        this.generator = gen;
        this.modid = modid;
    }

    public void add(ResourceLocation lootTableId, Consumer<LootBuilder> builderConsumer) {
        LootBuilder builder = new LootBuilder();
        builderConsumer.accept(builder);
        lootTables.put(lootTableId, builder.build());
    }

    public abstract void registerLootTables();

    @Override
    public void run(HashCache cache) throws IOException {
        lootTables.clear();
        registerLootTables();
        List<LootTableKey> keys = new ArrayList<>();
        lootTables.forEach((resourceLocation, lootTableFile) -> {
            Path out = generator.getOutputFolder()
                    .resolve("data/" + resourceLocation.getNamespace() + "/vault_configs/gen/loot_tables/" + resourceLocation.getPath() + ".json");
            keys.add(LootTableKey.create(resourceLocation, ResourceLocUtils.formatReadableName(resourceLocation)));

            try {
                DataProvider.save(gson, cache, gson.toJsonTree(lootTableFile), out);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        JsonObject keysOutput = new JsonObject();
        JsonArray keyArray = new JsonArray();
        keys.forEach(lootTableKey -> {
            JsonObject key = new JsonObject();
            key.addProperty("id", lootTableKey.getId().toString());
            key.addProperty("name", lootTableKey.getName());
            key.addProperty("1.0", lootTableKey.getId().toString());
            keyArray.add(key);
        });
        keysOutput.add("keys", keyArray);

        Path keysOut = generator.getOutputFolder()
                .resolve("data/" + modid + "/vault_configs/vault_loot_tables/loot_tables.json");
        DataProvider.save(gson, cache, keysOutput, keysOut);


    }

    @Override
    public String getName() {
        return modid + " Loot Table Data Provider";
    }

    public static class LootTableFile {
        public List<LootEntry> entries = new ArrayList<>();
    }

    public static class LootEntry {
        public Roll roll;
        public List<LootPool> pool = new ArrayList<>();
    }

    public static class Roll {
        public String type = "uniform";
        public int min;
        public int max;

        public Roll(int min, int max) {
            this.min = min;
            this.max = max;
        }
    }

    public static class LootPool {
        public int weight;
        public List<LootPoolItem> pool = new ArrayList<>();

        public LootPool(int weight) {
            this.weight = weight;
        }
    }

    public static class LootPoolItem {
        public int weight;
        public LootItem item;

        public LootPoolItem(int weight, LootItem item) {
            this.weight = weight;
            this.item = item;
        }
    }

    public static class LootItem {
        public String id;
        public Count count;
        public Map<String, Object> nbt;

        public LootItem(String id, Count count) {
            this.id = id;
            this.count = count;
        }
    }

    public static class Count {
        public String type = "uniform";
        public int min;
        public int max;

        public Count(int min, int max) {
            this.min = min;
            this.max = max;
        }
    }


    public static class LootBuilder {

        private final LootTableFile file = new LootTableFile();

        public LootEntry entry(Consumer<EntryBuilder> builderConsumer) {
            LootEntry e = new LootEntry();
            EntryBuilder builder = new EntryBuilder(e);
            builderConsumer.accept(builder);
            file.entries.add(builder.build());
            return e;
        }

        public LootTableFile build() {
            return file;
        }
    }

    public static class EntryBuilder {
        private final LootEntry entry;

        public EntryBuilder(LootEntry entry) { this.entry = entry; }

        public EntryBuilder rolls(int min, int max) {
            entry.roll = new Roll(min, max);
            return this;
        }

        public EntryBuilder pool(int weight, Consumer<PoolBuilder> c) {
            LootPool p = new LootPool(weight);
            entry.pool.add(p);
            PoolBuilder pb = new PoolBuilder(p);
            c.accept(pb);
            return this;
        }

        public LootEntry build() {
            if(entry.roll == null) {
                entry.roll = new Roll(1, 1);
            }

            return entry;
        }
    }

    public static class PoolBuilder {
        private final LootPool pool;

        public PoolBuilder(LootPool pool) { this.pool = pool; }

        public PoolBuilder item(int weight, String id, int min, int max) {
            pool.pool.add(new LootPoolItem(weight, new LootItem(id, new Count(min, max))));
            return this;
        }

        public PoolBuilder itemNbt(int weight, String id, int min, int max, Consumer<LinkedHashMap<String,Object>> nbtConsumer) {
            LootItem item = new LootItem(id, new Count(min, max));
            LinkedHashMap<String, Object> nbt = new LinkedHashMap<>();
            nbtConsumer.accept(nbt);
            item.nbt = nbt;
            pool.pool.add(new LootPoolItem(weight, item));
            return this;
        }
    }

}
