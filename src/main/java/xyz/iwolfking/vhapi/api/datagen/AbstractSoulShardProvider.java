package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.SoulShardConfig;
import net.minecraft.data.DataGenerator;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;

import java.util.Set;

public abstract class AbstractSoulShardProvider extends AbstractVaultConfigDataProvider<AbstractSoulShardProvider.Builder> {
    protected AbstractSoulShardProvider(DataGenerator generator, String modid) {
        super(generator, modid, "soul_shard", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Black Market (Normal) Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<SoulShardConfig> {
        private Set<SoulShardConfig.Trades> trades;

        public Builder() {
            super(SoulShardConfig::new);
        }


        @Override
        protected void configureConfig(SoulShardConfig config) {

        }

    }
}
