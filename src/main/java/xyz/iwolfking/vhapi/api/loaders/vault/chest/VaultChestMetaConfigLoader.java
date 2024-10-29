package xyz.iwolfking.vhapi.api.loaders.vault.chest;

import iskallia.vault.config.VaultMetaChestConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.VaultMetaChestConfigAccessor;

public class VaultChestMetaConfigLoader extends VaultConfigProcessor<VaultMetaChestConfig> {
    public VaultChestMetaConfigLoader() {
        super(new VaultMetaChestConfig(), "vault/chest_meta");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(VaultMetaChestConfig config : this.CUSTOM_CONFIGS.values()) {
            ((VaultMetaChestConfigAccessor)ModConfigs.VAULT_CHEST_META).getCatalystChances().putAll(((VaultMetaChestConfigAccessor)config).getCatalystChances());
            if(config.getCatalystMinLevel() != 20) {
                ((VaultMetaChestConfigAccessor) ModConfigs.VAULT_CHEST_META).setCatalystMinLevel(config.getCatalystMinLevel());
            }
        }
        super.afterConfigsLoad(event);
    }
}
