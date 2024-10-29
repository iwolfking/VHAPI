package xyz.iwolfking.vhapi.api.loaders.skills;

import iskallia.vault.config.AbilitiesConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

public class AbilitiesConfigLoader extends VaultConfigProcessor<AbilitiesConfig> {
    public AbilitiesConfigLoader() {
        super(new AbilitiesConfig(), "abilities/abilities");
    }


    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(AbilitiesConfig config : this.CUSTOM_CONFIGS.values()) {
            ModConfigs.ABILITIES.tree.skills.addAll(config.tree.skills);
            super.afterConfigsLoad(event);
        }
    }
}
