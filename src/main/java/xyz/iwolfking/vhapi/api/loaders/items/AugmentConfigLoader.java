package xyz.iwolfking.vhapi.api.loaders.items;

import iskallia.vault.config.AugmentConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.AugmentConfigAccessor;


public class AugmentConfigLoader extends VaultConfigProcessor<AugmentConfig> {
    public AugmentConfigLoader() {
        super(new AugmentConfig(), "augments");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(AugmentConfig config : this.CUSTOM_CONFIGS.values()) {
            ((AugmentConfigAccessor)ModConfigs.AUGMENT).getDrops().putAll(((AugmentConfigAccessor)config).getDrops());
        }
        super.afterConfigsLoad(event);
    }
}
