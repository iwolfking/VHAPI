package xyz.iwolfking.vhapi.api.lib.config.loaders.items;

import iskallia.vault.config.AugmentConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vhapi.api.lib.loaders.VaultConfigDataLoaderRF;
import xyz.iwolfking.vhapi.mixin.accessors.AugmentConfigAccessor;

import java.util.HashMap;


public class AugmentConfigLoader extends VaultConfigDataLoaderRF<AugmentConfig> {
    public AugmentConfigLoader(String namespace) {
        super(new AugmentConfig(), "augments",  namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(AugmentConfig config : this.CUSTOM_CONFIGS.values()) {
            ((AugmentConfigAccessor)ModConfigs.AUGMENT).getDrops().putAll(((AugmentConfigAccessor)config).getDrops());
        }
    }
}
