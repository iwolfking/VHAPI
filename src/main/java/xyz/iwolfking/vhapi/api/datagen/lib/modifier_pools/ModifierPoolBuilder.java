package xyz.iwolfking.vhapi.api.datagen.lib.modifier_pools;

import java.util.*;

public class ModifierPoolBuilder {

    private final Map<String, List<PoolLevel>> poolMap = new LinkedHashMap<>();

    public ModifierPoolBuilder pool(String name, PoolConsumer consumer) {
        PoolLevelBuilder builder = new PoolLevelBuilder();
        consumer.accept(builder);
        poolMap.put(name, builder.levels);
        return this;
    }

    public ModifierPoolFile build() {
        return new ModifierPoolFile(poolMap);
    }

    public static class PoolLevelBuilder {
        private final List<PoolLevel> levels = new ArrayList<>();

        public PoolLevelBuilder level(int level, EntryListConsumer consumer) {
            EntryListBuilder b = new EntryListBuilder(level);
            consumer.accept(b);
            levels.add(b.build());
            return this;
        }
    }

    public static class EntryListBuilder {
        private final int level;
        private final List<PoolEntry> entries = new ArrayList<>();

        public EntryListBuilder(int level) {
            this.level = level;
        }

        public EntryListBuilder entry(int min, int max, PoolValueListConsumer consumer) {
            PoolValueListBuilder vb = new PoolValueListBuilder(min, max);
            consumer.accept(vb);
            entries.add(vb.build());
            return this;
        }

        public PoolLevel build() {
            return new PoolLevel(level, entries);
        }
    }

    public static class PoolValueListBuilder {
        private final int min, max;
        private final List<PoolWeight> values = new ArrayList<>();

        public PoolValueListBuilder(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public PoolValueListBuilder add(String value, int weight) {
            values.add(new PoolWeight(value, weight));
            return this;
        }

        public PoolEntry build() {
            return new PoolEntry(min, max, values);
        }
    }

    @FunctionalInterface public interface PoolConsumer {
        void accept(PoolLevelBuilder builder);
    }

    @FunctionalInterface public interface EntryListConsumer {
        void accept(EntryListBuilder builder);
    }

    @FunctionalInterface public interface PoolValueListConsumer {
        void accept(PoolValueListBuilder builder);
    }
}
