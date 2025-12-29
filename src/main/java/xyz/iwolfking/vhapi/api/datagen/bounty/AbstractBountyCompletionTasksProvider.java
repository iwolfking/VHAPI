package xyz.iwolfking.vhapi.api.datagen.bounty;

import iskallia.vault.bounty.task.CompletionTask;
import iskallia.vault.bounty.task.Task;
import iskallia.vault.bounty.task.properties.CompletionProperties;
import iskallia.vault.config.Config;
import iskallia.vault.config.TooltipConfig;
import iskallia.vault.config.bounty.task.CompletionTaskConfig;
import iskallia.vault.config.bounty.task.TaskConfig;
import iskallia.vault.config.bounty.task.entry.GenericEntry;
import iskallia.vault.config.bounty.task.entry.TaskEntry;
import iskallia.vault.config.entry.IntRangeEntry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.datagen.AbstractVaultConfigDataProvider;
import xyz.iwolfking.vhapi.api.datagen.bounty.lib.AbstractBountyTaskDataProvider;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.TooltipConfigAccessor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class AbstractBountyCompletionTasksProvider extends AbstractBountyTaskDataProvider<TaskEntry<String>, CompletionProperties, CompletionTaskConfig> {

    protected AbstractBountyCompletionTasksProvider(DataGenerator generator, String modid) {
        super(generator, modid, "bounty/tasks/completion", Builder::new);
    }

    public static class Builder extends AbstractBountyTaskDataProvider.BountyTaskConfigBuilder<TaskEntry<String>, CompletionProperties, CompletionTaskConfig> {

        public Builder() {
            super(CompletionTaskConfig::new);
        }

        public Builder addTasks(int level, GenericEntry<String> genericEntry, int weight, Consumer<AbstractBountyTaskDataProvider.TaskEntryBuilder<String>> entriesConsumer) {
            TaskEntryBuilder<String> builder = new TaskEntryBuilder<>(genericEntry, weight);
            entriesConsumer.accept(builder);
            entryMap.put(level, builder.build());
            return this;
        }
    }


}
