package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.SoulShardConfig;
import iskallia.vault.config.TooltipConfig;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.loaders.shops.NormalBlackMarketConfigLoader;
import xyz.iwolfking.vhapi.mixin.accessors.TooltipConfigAccessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class AbstractSoulShardProvider extends AbstractVaultConfigDataProvider {
    protected AbstractSoulShardProvider(DataGenerator generator, String modid) {
        super(generator, modid, "soul_shard");
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Black Market (Normal) Data Provider";
    }

    public static class SoulShardConfigBuilder {
        private Set<SoulShardConfig.Trades> trades;


        public SoulShardConfig build() {
            SoulShardConfig newConfig = new SoulShardConfig();
            return newConfig;
        }

    }
}
