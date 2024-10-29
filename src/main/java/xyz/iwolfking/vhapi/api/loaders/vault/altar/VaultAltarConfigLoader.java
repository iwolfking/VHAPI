package xyz.iwolfking.vhapi.api.loaders.vault.altar;

import iskallia.vault.config.VaultAltarConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

public class VaultAltarConfigLoader extends VaultConfigProcessor<VaultAltarConfig> {
    public VaultAltarConfigLoader() {
        super(new VaultAltarConfig(), "vault/altar");
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
        super.afterConfigsLoad(event);
    }
}
