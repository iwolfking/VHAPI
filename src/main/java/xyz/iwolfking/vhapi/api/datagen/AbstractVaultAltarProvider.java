package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.VaultAltarConfig;
import iskallia.vault.config.VaultDiffuserConfig;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractVaultAltarProvider extends AbstractVaultConfigDataProvider {
    protected AbstractVaultAltarProvider(DataGenerator generator, String modid) {
        super(generator, modid, "vault/altar");
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Vault Altar Data Provider";
    }

    public static class VaultAltarConfigBuilder {
        public List<VaultAltarConfig.Interface> INTERFACES;

        public VaultAltarConfigBuilder add(VaultAltarConfig.Interface altarInterface) {
            INTERFACES.add(altarInterface);
            return this;
        }

        public VaultAltarConfig build() {
            VaultAltarConfig newConfig = new VaultAltarConfig();
            newConfig.INTERFACES.addAll(INTERFACES);
            return newConfig;
        }

    }
}
