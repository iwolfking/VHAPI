package xyz.iwolfking.vhapi.api.loaders.loot;

import iskallia.vault.config.LootInfoConfig;
import iskallia.vault.init.ModConfigs;

import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.LootInfoConfigAccessor;


public class LootInfoConfigLoader extends VaultConfigProcessor<LootInfoConfig> {

    public LootInfoConfigLoader() {
        super(new LootInfoConfig(), "loot_info");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(LootInfoConfig config : this.CUSTOM_CONFIGS.values()) {
            ((LootInfoConfigAccessor)ModConfigs.LOOT_INFO_CONFIG).getLootInfoMapModifiable().putAll(config.getLootInfoMap());
        }
        super.afterConfigsLoad(event);
    }
}
