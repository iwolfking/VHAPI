package xyz.iwolfking.vhapi.api.loaders.card;

import iskallia.vault.config.card.BoosterPackConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;


public class BoosterPacksConfigLoader extends VaultConfigProcessor<BoosterPackConfig> {
    public BoosterPacksConfigLoader() {
        super(new BoosterPackConfig(), "card/booster_packs");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(BoosterPackConfig config : this.CUSTOM_CONFIGS.values()) {
            ModConfigs.BOOSTER_PACK.getValues().putAll(config.getValues());
        }
        super.afterConfigsLoad(event);
    }
}
