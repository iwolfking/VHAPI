package xyz.iwolfking.vhapi.api.datagen.bounty.lib;

import iskallia.vault.bounty.task.properties.TaskProperties;
import iskallia.vault.config.bounty.task.TaskConfig;
import iskallia.vault.config.bounty.task.entry.GenericEntry;
import iskallia.vault.config.bounty.task.entry.TaskEntry;
import iskallia.vault.config.entry.IntRangeEntry;
import iskallia.vault.config.entry.LevelEntryMap;
import net.minecraft.data.DataGenerator;
import xyz.iwolfking.vhapi.api.datagen.AbstractVaultConfigDataProvider;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.TaskConfigAccessor;

import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class AbstractBountyTaskDataProvider<E extends TaskEntry<?>, T extends TaskProperties, C extends TaskConfig<E, T>> extends AbstractVaultConfigDataProvider<AbstractBountyTaskDataProvider.BountyTaskConfigBuilder<E, T, C>> {

    protected AbstractBountyTaskDataProvider(DataGenerator generator, String modid, String configPath, Supplier<BountyTaskConfigBuilder<E, T, C>> builderFactory) {
        super(generator, modid, configPath, builderFactory);
    }

    public abstract static class BountyTaskConfigBuilder<E extends TaskEntry<?>, T extends TaskProperties, C extends TaskConfig<E, T>> extends VaultConfigBuilder<C> {
        protected LevelEntryMap<E> entryMap = new LevelEntryMap<>();

        public BountyTaskConfigBuilder(Supplier<C> factory) {
            super(factory);
        }

        public BountyTaskConfigBuilder<E,T,C> addTasks(int level, E entry) {
            entryMap.put(level, entry);
            return this;
        }


        @Override
        protected void configureConfig(C config) {
            ((TaskConfigAccessor<E,T>)config).setLevels(entryMap);
        }
    }

    public static class TaskEntryBuilder<T> {
        protected TaskEntry<T> entry;

        public TaskEntryBuilder(GenericEntry<T> genericEntry, int weight) {
            this.entry = new TaskEntry<>(genericEntry, weight);
        }

        public TaskEntryBuilder<T> addEntry(GenericEntry<T> genericEntry, int weight) {
            entry.addEntry(genericEntry, weight);
            return this;
        }

        public TaskEntry<T> build() {
            return entry;
        }
    }
}
