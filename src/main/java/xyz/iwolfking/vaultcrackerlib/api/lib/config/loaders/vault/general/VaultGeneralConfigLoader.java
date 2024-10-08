package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.vault.general;

import iskallia.vault.config.VaultGeneralConfig;
import iskallia.vault.config.tool.ToolPulverizingConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.VaultGeneralConfigAccessor;

import java.util.HashMap;
import java.util.Map;

public class VaultGeneralConfigLoader extends VaultConfigDataLoader<VaultGeneralConfig> {

    public VaultGeneralConfigLoader(String namespace) {
        super(new VaultGeneralConfig(), "vault/general", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ResourceLocation key : this.CUSTOM_CONFIGS.keySet()) {
            if(key.getPath().contains("overwrite")) {
                ModConfigs.VAULT_GENERAL = CUSTOM_CONFIGS.get(key);
            }
            else if(key.getPath().contains("remove")) {
                ((VaultGeneralConfigAccessor)ModConfigs.VAULT_GENERAL).getBlockBlacklist().removeAll(((VaultGeneralConfigAccessor)CUSTOM_CONFIGS.get(key)).getBlockBlacklist());
                ((VaultGeneralConfigAccessor)ModConfigs.VAULT_GENERAL).getItemBlacklist().removeAll(((VaultGeneralConfigAccessor)CUSTOM_CONFIGS.get(key)).getItemBlacklist());
            }
            else {
                ((VaultGeneralConfigAccessor)ModConfigs.VAULT_GENERAL).getBlockBlacklist().addAll(((VaultGeneralConfigAccessor)CUSTOM_CONFIGS.get(key)).getBlockBlacklist());
                ((VaultGeneralConfigAccessor)ModConfigs.VAULT_GENERAL).getItemBlacklist().addAll(((VaultGeneralConfigAccessor)CUSTOM_CONFIGS.get(key)).getItemBlacklist());
            }
        }

    }
}
