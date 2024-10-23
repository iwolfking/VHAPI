package xyz.iwolfking.vhapi.api.loaders.vault.catalyst;

import iskallia.vault.config.CatalystConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

public class CatalystConfigLoader extends VaultConfigProcessor<CatalystConfig> {
    public CatalystConfigLoader() {
        super(new CatalystConfig(), "catalyst");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(CatalystConfig config : this.CUSTOM_CONFIGS.values()) {
            ModConfigs.CATALYST.pools.putAll(config.pools);
        }

    }
}
