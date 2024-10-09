package xyz.iwolfking.vhapi.api.lib.config.loaders.vault.chest;

import iskallia.vault.config.VaultMetaChestConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vhapi.mixin.accessors.VaultMetaChestConfigAccessor;

import java.util.HashMap;

public class VaultChestMetaConfigLoader extends VaultConfigDataLoader<VaultMetaChestConfig> {
    public VaultChestMetaConfigLoader(String namespace) {
        super(new VaultMetaChestConfig(), "vault/chest_meta", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(VaultMetaChestConfig config : this.CUSTOM_CONFIGS.values()) {
            ((VaultMetaChestConfigAccessor)ModConfigs.VAULT_CHEST_META).getCatalystChances().putAll(((VaultMetaChestConfigAccessor)config).getCatalystChances());
            if(config.getCatalystMinLevel() != 20) {
                ((VaultMetaChestConfigAccessor) ModConfigs.VAULT_CHEST_META).setCatalystMinLevel(config.getCatalystMinLevel());
            }
        }

    }
}
