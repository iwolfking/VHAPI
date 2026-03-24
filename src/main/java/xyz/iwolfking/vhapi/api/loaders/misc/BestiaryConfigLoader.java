package xyz.iwolfking.vhapi.api.loaders.misc;

import iskallia.vault.config.BestiaryConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

public class BestiaryConfigLoader extends VaultConfigProcessor<BestiaryConfig> {
    public BestiaryConfigLoader() {
        super(new BestiaryConfig(), "bestiary");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        this.CUSTOM_CONFIGS.forEach((resourceLocation, bestiaryConfig) -> {
            ModConfigs.BESTIARY.getEntities().addAll(bestiaryConfig.getEntities());
        });
        super.afterConfigsLoad(event);
    }
}
