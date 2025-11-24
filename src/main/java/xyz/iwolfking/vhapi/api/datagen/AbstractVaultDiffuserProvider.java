package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.VaultDiffuserConfig;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.VaultDiffuserConfigAccessor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class AbstractVaultDiffuserProvider extends AbstractVaultConfigDataProvider<AbstractVaultDiffuserProvider.Builder> {
    protected AbstractVaultDiffuserProvider(DataGenerator generator, String modid) {
        super(generator, modid, "vault_diffuser", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Vault Diffuser Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<VaultDiffuserConfig> {

        private final Map<ResourceLocation, Integer> diffuserOutputMap = new HashMap<>();

        public Builder() {
            super(VaultDiffuserConfig::new);
        }

        public Builder add(ResourceLocation itemName, Integer soulValue) {
            diffuserOutputMap.put(itemName, soulValue);
            return this;
        }

        @Override
        protected void configureConfig(VaultDiffuserConfig config) {
            ((VaultDiffuserConfigAccessor)config).setDiffuserOutputMap(diffuserOutputMap);

        }

    }
}
