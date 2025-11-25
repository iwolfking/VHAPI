package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.TemporalShardConfig;
import iskallia.vault.config.TooltipConfig;
import iskallia.vault.config.entry.LevelEntryList;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.api.datagen.lib.WeightedLevelEntryListBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.TemporalShardConfigAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.TooltipConfigAccessor;

import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractTemporalShardProvider extends AbstractVaultConfigDataProvider<AbstractTemporalShardProvider.Builder> {
    protected AbstractTemporalShardProvider(DataGenerator generator, String modid) {
        super(generator, modid, "temporal_shard", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Temporal Shard Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<TemporalShardConfig> {
        LevelEntryList<TemporalShardConfig.Level> entries = new LevelEntryList<>();

        public Builder() {
            super(TemporalShardConfig::new);
        }

        public Builder add(TemporalShardConfig.Level entry) {
            entries.add(entry);
            return this;
        }

        @Override
        protected void configureConfig(TemporalShardConfig config) {
            ((TemporalShardConfigAccessor)config).setModifiers(entries);
        }
    }
}
