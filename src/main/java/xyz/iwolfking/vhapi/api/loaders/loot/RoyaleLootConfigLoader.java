package xyz.iwolfking.vhapi.api.loaders.loot;

import iskallia.vault.config.RoyaleLootConfig;
import iskallia.vault.config.RoyalePresetConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

public class RoyaleLootConfigLoader extends VaultConfigProcessor<RoyaleLootConfig> {
    public RoyaleLootConfigLoader() {
        super(new RoyaleLootConfig(), "royale_loot");
    }


    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(RoyaleLootConfig config : this.CUSTOM_CONFIGS.values()) {
            config.presets.forEach(ModConfigs.ROYALE_LOOT.presets::add);
            ModConfigs.ROYALE_LOOT.numberOfSelections = config.numberOfSelections;
            super.afterConfigsLoad(event);
        }
    }
}
