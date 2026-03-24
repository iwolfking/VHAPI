package xyz.iwolfking.vhapi.api.datagen.lib;

import iskallia.vault.config.entry.LevelEntryList;

import java.util.function.Supplier;

public class LeveledBuilder<T extends LevelEntryList.ILevelEntry> {

    public LevelEntryList<T> levels = new LevelEntryList<>();

    public LeveledBuilder<T> add(Supplier<T> entryFactory) {
        levels.add(entryFactory.get());
        return this;
    }


}
