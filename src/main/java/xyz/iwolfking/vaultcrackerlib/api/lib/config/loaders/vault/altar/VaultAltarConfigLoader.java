package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.vault.altar;

import iskallia.vault.config.VaultAltarConfig;
import iskallia.vault.config.VaultCrystalConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.VaultCrystalConfigAccessor;

import java.util.HashMap;
import java.util.Map;

public class VaultAltarConfigLoader extends VaultConfigDataLoader<VaultAltarConfig> {
    public VaultAltarConfigLoader(String namespace) {
        super(new VaultAltarConfig(), "vault/altar", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ResourceLocation key : this.CUSTOM_CONFIGS.keySet()) {
            if(key.getPath().contains("overwrite")) {
                ModConfigs.VAULT_ALTAR = CUSTOM_CONFIGS.get(key);
            }
            else if(key.getPath().contains("remove")) {
                //Unsupported for now
            }
            else {
                ModConfigs.VAULT_ALTAR.INTERFACES.addAll(CUSTOM_CONFIGS.get(key).INTERFACES);
            }
        }

    }
}
