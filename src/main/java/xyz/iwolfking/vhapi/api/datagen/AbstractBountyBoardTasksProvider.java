package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.bounty.BountyBoardTasksConfig;
import iskallia.vault.gear.VaultGearRarity;
import iskallia.vault.task.Task;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.data.DataGenerator;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.BountyBoardTasksConfigAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.BountyBoardTasksEntryAccessor;

public abstract class AbstractBountyBoardTasksProvider
        extends AbstractVaultConfigDataProvider<AbstractBountyBoardTasksProvider.Builder> {
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

        public EntryBuilder task(String id) {
            return new EntryBuilder(id);
        }

        public Builder addTask(String id, Consumer<EntryBuilder> configurator) {
            EntryBuilder builder = new EntryBuilder(id);
            configurator.accept(builder);
            this.tasks.add(builder.build());
            return this;
        }

        public Builder addTask(BountyBoardTasksConfig.Entry entry) {
            this.tasks.add(entry);
            return this;
        }

        @Override
        protected void configureConfig(BountyBoardTasksConfig config) {
            ((BountyBoardTasksConfigAccessor) config).vhapi$setTasks(new ArrayList<>(this.tasks));
        }
    }

    public static class EntryBuilder {
        private final BountyBoardTasksConfig.Entry entry = new BountyBoardTasksConfig.Entry();
        private final BountyBoardTasksEntryAccessor accessor = (BountyBoardTasksEntryAccessor) this.entry;

        public EntryBuilder(String id) {
            accessor.setId(id);
            accessor.setWeight(1.0F);
            accessor.setMaxLevel(-1);
            accessor.setEffort(BountyBoardTasksConfig.Entry.ANY_EFFORT);
        }

        public EntryBuilder title(@Nullable String title) {
            accessor.setTitle(title);
            return this;
        }

        public EntryBuilder type(@Nullable String type) {
            accessor.setType(type);
            return this;
        }

        public EntryBuilder kind(@Nullable BountyBoardTasksConfig.Kind kind) {
            accessor.setKind(kind);
            return this;
        }

        public EntryBuilder normalNumber(int normalNumber) {
            accessor.setNormalNumber(normalNumber);
            return this;
        }

        public EntryBuilder maxRarity(@Nullable VaultGearRarity maxRarity) {
            accessor.setMaxRarity(maxRarity);
            return this;
        }

        public EntryBuilder weight(float weight) {
            accessor.setWeight(weight);
            return this;
        }

        public EntryBuilder minLevel(int minLevel) {
            accessor.setMinLevel(minLevel);
            return this;
        }

        public EntryBuilder maxLevel(int maxLevel) {
            accessor.setMaxLevel(maxLevel);
            return this;
        }

        public EntryBuilder minGreedTier(int minGreedTier) {
            accessor.setMinGreedTier(minGreedTier);
            return this;
        }

        public EntryBuilder targetGrowthPerGreedTier(float targetGrowthPerGreedTier) {
            accessor.setTargetGrowthPerGreedTier(targetGrowthPerGreedTier);
            return this;
        }

        public EntryBuilder flavor(@Nullable String flavor) {
            accessor.setFlavor(flavor);
            return this;
        }

        public EntryBuilder cardIcon(@Nullable String cardIcon) {
            accessor.setCardIcon(cardIcon);
            return this;
        }

        public EntryBuilder effort(int effort) {
            accessor.setEffort(effort);
            return this;
        }

        public EntryBuilder task(@Nullable Task task) {
            accessor.setTask(task);
            return this;
        }

        public BountyBoardTasksConfig.Entry build() {
            return this.entry;
        }
    }
}