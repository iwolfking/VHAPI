package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.VaultMod;
import iskallia.vault.config.VaultDiffuserConfig;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractVaultDiffuserProvider extends AbstractVaultConfigDataProvider {
    protected AbstractVaultDiffuserProvider(DataGenerator generator, String modid) {
        super(generator, modid, "vault_diffuser");
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Vault Diffuser Data Provider";
    }

    public static class VaultDiffuserConfigBuilder {

        private final Map<ResourceLocation, Integer> diffuserOutputMap = new HashMap<>();

        public VaultDiffuserConfigBuilder add(ResourceLocation itemName, Integer soulValue) {
            diffuserOutputMap.put(itemName, soulValue);
            return this;
        }

        public VaultDiffuserConfig build() {
            VaultDiffuserConfig newConfig = new VaultDiffuserConfig();
            newConfig.getDiffuserOutputMap().putAll(diffuserOutputMap);
            return newConfig;
        }

    }
}
