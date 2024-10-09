package xyz.iwolfking.vhapi.api.lib.config.loaders.workstation;

import iskallia.vault.config.VaultDiffuserConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.lib.loaders.VaultConfigDataLoader;
import java.util.HashMap;

public class VaultDiffuserConfigLoader extends VaultConfigDataLoader<VaultDiffuserConfig> {
    public VaultDiffuserConfigLoader(String namespace) {
        super(new VaultDiffuserConfig(), "vault_diffuser", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ResourceLocation key : this.CUSTOM_CONFIGS.keySet()) {
            if(key.getPath().contains("overwrite")) {
                ModConfigs.VAULT_DIFFUSER = CUSTOM_CONFIGS.get(key);
            }
            else if(key.getPath().contains("remove")) {
                for(ResourceLocation itemKey : CUSTOM_CONFIGS.get(key).getDiffuserOutputMap().keySet()) {
                    ModConfigs.VAULT_DIFFUSER.getDiffuserOutputMap().remove(itemKey);
                }
            }
            else {
                ModConfigs.VAULT_DIFFUSER.getDiffuserOutputMap().putAll(CUSTOM_CONFIGS.get(key).getDiffuserOutputMap());
            }
        }

    }
}
