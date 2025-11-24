package xyz.iwolfking.vhapi.api.datagen.lib;

import iskallia.vault.config.entry.LevelEntryList;
import iskallia.vault.core.util.WeightedList;

import java.util.function.BiFunction;
import java.util.function.Consumer;

public class WeightedLevelEntryListBuilder<T extends LevelEntryList.ILevelEntry, W> extends LevelEntryList<T> {
    public LevelEntryList<T> entries = new LevelEntryList<>();
    private final BiFunction<Integer, WeightedList<W>, T> entryFactory;

    public WeightedLevelEntryListBuilder(BiFunction<Integer, WeightedList<W>, T> entryFactory) {
        this.entryFactory = entryFactory;
    }

    public LevelEntryList<T> add(int level, Consumer<WeightedListBuilder<W>> builderConsumer) {
        WeightedListBuilder<W> builder = new WeightedListBuilder<>();
        builderConsumer.accept(builder);
        entries.add(entryFactory.apply(level, builder.build()));
        return this;
    }

    public LevelEntryList<T> build() {
        return entries;
    }
}
