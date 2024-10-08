package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.workstation;

import iskallia.vault.config.VaultDiffuserConfig;
import iskallia.vault.config.tool.ToolPulverizingConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;

import java.util.HashMap;
import java.util.Map;

public class VaultDiffuserConfigLoader extends VaultConfigDataLoader<VaultDiffuserConfig> {
    public VaultDiffuserConfigLoader(String namespace) {
        super(ModConfigs.VAULT_DIFFUSER, "vault_diffuser", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ResourceLocation key : this.CUSTOM_CONFIGS.keySet()) {
            if(key.getPath().contains("overwrite")) {
                ModConfigs.VAULT_DIFFUSER = CUSTOM_CONFIGS.get(key);
            }
            else if(key.getPath().contains("remove")) {
                for(ResourceLocation itemKey : CUSTOM_CONFIGS.get(key).getDiffuserOutputMap().keySet()) {
                    if(ModConfigs.VAULT_DIFFUSER.getDiffuserOutputMap().containsKey(itemKey)) {
                        ModConfigs.VAULT_DIFFUSER.getDiffuserOutputMap().remove(itemKey);
                    }
                }
            }
            else {
                ModConfigs.VAULT_DIFFUSER.getDiffuserOutputMap().putAll(CUSTOM_CONFIGS.get(key).getDiffuserOutputMap());
            }
        }

    }
}
