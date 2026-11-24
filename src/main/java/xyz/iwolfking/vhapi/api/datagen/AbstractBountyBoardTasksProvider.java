package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.bounty.BountyBoardTasksConfig;
import iskallia.vault.gear.VaultGearRarity;
import iskallia.vault.task.Task;
import net.minecraft.data.DataGenerator;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.BountyBoardTasksConfigAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.BountyBoardTasksConfigEntryAccessor;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractBountyBoardTasksProvider extends AbstractVaultConfigDataProvider<AbstractBountyBoardTasksProvider.Builder> {
    protected AbstractBountyBoardTasksProvider(DataGenerator generator, String modid) {
        super(generator, modid, "bounty/tasks", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Bounty Board Tasks Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<BountyBoardTasksConfig> {
        public final List<BountyBoardTasksConfig.Entry> tasks = new ArrayList<>();

        public Builder() {
            super(BountyBoardTasksConfig::new);
        }

        public Builder addTask(BountyBoardTasksConfig.Entry entry) {
            this.tasks.add(entry);
            return this;
        }

        public Builder addTask(
                String id,
                String title,
                String type,
                BountyBoardTasksConfig.Kind kind,
                int normalNumber,
                VaultGearRarity maxRarity,
                int minLevel,
                int maxLevel,
                Task task,
                @Nullable Consumer<BountyBoardTasksConfigEntryAccessor> entryCustomizer
        ) {
            BountyBoardTasksConfig.Entry entry = new BountyBoardTasksConfig.Entry(
                    id, title, type, kind, normalNumber, maxRarity, minLevel, maxLevel, task
            );
            if (entryCustomizer != null) {
                entryCustomizer.accept((BountyBoardTasksConfigEntryAccessor) entry);
            }
            this.tasks.add(entry);
            return this;
        }

        @Override
        protected void configureConfig(BountyBoardTasksConfig config) {
            ((BountyBoardTasksConfigAccessor) config).vhapi$setTasks(new ArrayList<>(this.tasks));
        }
    }
}