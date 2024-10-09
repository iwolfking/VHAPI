package xyz.iwolfking.vhapi.api.lib.config.loaders.vault.catalyst;

import iskallia.vault.config.CatalystConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.lib.loaders.VaultConfigDataLoader;

import java.util.HashMap;

public class CatalystConfigLoader extends VaultConfigDataLoader<CatalystConfig> {
    public CatalystConfigLoader(String namespace) {
        super(new CatalystConfig(), "catalyst", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(CatalystConfig config : this.CUSTOM_CONFIGS.values()) {
            ModConfigs.CATALYST.pools.putAll(config.pools);
        }

    }
}
