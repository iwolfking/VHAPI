package xyz.iwolfking.vhapi.api.loaders.skills;

import iskallia.vault.config.PrestigePowersConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

public class PrestigePowersConfigLoader extends VaultConfigProcessor<PrestigePowersConfig> {
    public PrestigePowersConfigLoader() {
        super(new PrestigePowersConfig(), "prestige/powers");
    }


    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(PrestigePowersConfig config : this.CUSTOM_CONFIGS.values()) {
            ModConfigs.PRESTIGE_POWERS.tree.skills.addAll(config.tree.skills);
            super.afterConfigsLoad(event);
        }
    }
}
