package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.CatalystConfig;
import iskallia.vault.config.TooltipConfig;
import iskallia.vault.config.entry.LevelEntryList;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.api.datagen.lib.WeightedLevelEntryListBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.TooltipConfigAccessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractCatalystProvider extends AbstractVaultConfigDataProvider<AbstractCatalystProvider.Builder> {
    protected AbstractCatalystProvider(DataGenerator generator, String modid) {
        super(generator, modid, "catalysts", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Catalysts Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<CatalystConfig> {
        public Map<ResourceLocation, LevelEntryList<CatalystConfig.Pool>> pools = new HashMap<>();

        public Builder() {
            super(CatalystConfig::new);
        }

        public Builder addCatalystPool(ResourceLocation pool, Consumer<WeightedLevelEntryListBuilder<CatalystConfig.Pool, CatalystConfig.Entry>> builderConsumer) {
            WeightedLevelEntryListBuilder<CatalystConfig.Pool, CatalystConfig.Entry> builder = new WeightedLevelEntryListBuilder<>(CatalystConfig.Pool::new);
            builderConsumer.accept(builder);
            pools.put(pool, builder.build());
            return this;
        }

        @Override
        protected void configureConfig(CatalystConfig config) {
            config.pools = new HashMap<>();
            config.pools.putAll(pools);
        }
    }
}
