package xyz.iwolfking.vhapi.api.loaders.talents;

import iskallia.vault.config.TalentsConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

public class TalentConfigLoader extends VaultConfigProcessor<TalentsConfig> {
    public TalentConfigLoader() {
        super(new TalentsConfig(), "talents/talents");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(TalentsConfig config : this.CUSTOM_CONFIGS.values()) {
            ModConfigs.TALENTS.tree.skills.addAll(config.tree.skills);
        }
        super.afterConfigsLoad(event);
    }
}
