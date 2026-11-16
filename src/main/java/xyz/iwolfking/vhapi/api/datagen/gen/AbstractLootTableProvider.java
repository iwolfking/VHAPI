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
        public Number weight;
        public List<LootPoolItem> pool = new ArrayList<>();

        public LootPool(int weight) {
            this.weight = weight;
        }

        /** A sub-pool with a fractional weight, such as a rare fifth pool far below the others. */
        public LootPool(double weight) {
            this.weight = weight;
        }
    }

    public static class LootPoolItem {
        public Number weight;
        public LootItem item;

        public LootPoolItem(int weight, LootItem item) {
            this.weight = weight;
            this.item = item;
        }

        /** An entry with a fractional weight. */
        public LootPoolItem(double weight, LootItem item) {
            this.weight = weight;
            this.item = item;
        }
    }

    public static class LootItem {
        public String id;
        public Count count;

        /** The item's NBT: a map written as a JSON object, or a string holding raw SNBT. */
        public Object nbt;

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
            return this.addPool(new LootPool(weight), c);
        }

        /** Adds a sub-pool with a fractional weight. */
        public EntryBuilder pool(double weight, Consumer<PoolBuilder> c) {
            return this.addPool(new LootPool(weight), c);
        }

        private EntryBuilder addPool(LootPool p, Consumer<PoolBuilder> c) {
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

        /** Adds an item with a fractional weight. */
        public PoolBuilder item(double weight, String id, int min, int max) {
            pool.pool.add(new LootPoolItem(weight, new LootItem(id, new Count(min, max))));
            return this;
        }

        public PoolBuilder itemNbt(int weight, String id, int min, int max, Consumer<LinkedHashMap<String,Object>> nbtConsumer) {
            pool.pool.add(new LootPoolItem(weight, itemWithNbt(id, min, max, nbtConsumer)));
            return this;
        }

        /** Adds an item with NBT and a fractional weight. */
        public PoolBuilder itemNbt(double weight, String id, int min, int max, Consumer<LinkedHashMap<String,Object>> nbtConsumer) {
            pool.pool.add(new LootPoolItem(weight, itemWithNbt(id, min, max, nbtConsumer)));
            return this;
        }

        /** Adds an item whose NBT is written as a raw SNBT string, kept exactly as given. */
        public PoolBuilder itemSnbt(int weight, String id, int min, int max, String snbt) {
            pool.pool.add(new LootPoolItem(weight, itemWithSnbt(id, min, max, snbt)));
            return this;
        }

        /** Adds an item whose NBT is a raw SNBT string, with a fractional weight. */
        public PoolBuilder itemSnbt(double weight, String id, int min, int max, String snbt) {
            pool.pool.add(new LootPoolItem(weight, itemWithSnbt(id, min, max, snbt)));
            return this;
        }

        private static LootItem itemWithNbt(String id, int min, int max, Consumer<LinkedHashMap<String,Object>> nbtConsumer) {
            LootItem item = new LootItem(id, new Count(min, max));
            LinkedHashMap<String, Object> nbt = new LinkedHashMap<>();
            nbtConsumer.accept(nbt);
            item.nbt = nbt;
            return item;
        }

        private static LootItem itemWithSnbt(String id, int min, int max, String snbt) {
            LootItem item = new LootItem(id, new Count(min, max));
            item.nbt = snbt;
            return item;
        }
    }

}
