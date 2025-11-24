package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.VaultAltarConfig;
import net.minecraft.data.DataGenerator;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;

import java.util.List;
import java.util.function.Supplier;

public abstract class AbstractVaultAltarProvider extends AbstractVaultConfigDataProvider<AbstractVaultAltarProvider.Builder> {
    protected AbstractVaultAltarProvider(DataGenerator generator, String modid) {
        super(generator, modid, "vault/altar", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Vault Altar Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<VaultAltarConfig> {
        public List<VaultAltarConfig.Interface> INTERFACES;

        public Builder() {
            super(VaultAltarConfig::new);
        }

        public Builder add(VaultAltarConfig.Interface altarInterface) {
            INTERFACES.add(altarInterface);
            return this;
        }

        @Override
        protected void configureConfig(VaultAltarConfig config) {
            config.INTERFACES.addAll(INTERFACES);
        }

    }
}
