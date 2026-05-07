package xyz.iwolfking.vhapi.api.loaders.vault.chest;

import iskallia.vault.config.VaultChestConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.api.util.LevelEntryListHelper;

public class VaultChestConfigLoader extends VaultConfigProcessor<VaultChestConfig> {
    public VaultChestConfigLoader() {
        super(new VaultChestConfig("vault_chest"), "vault/chest");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        this.CUSTOM_CONFIGS.forEach((resourceLocation, vaultChestConfig) -> {
            LevelEntryListHelper.mergeList(
                    vaultChestConfig.LEVELS,
                    ModConfigs.VAULT_CHEST.LEVELS,
                    level -> level.level,
                    level -> level.pool
            );


        });
        super.afterConfigsLoad(event);
    }
}
