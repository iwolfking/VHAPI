package xyz.iwolfking.vhapi.api.loaders.expertises;

import iskallia.vault.config.ExpertisesConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

public class ExpertiseConfigLoader extends VaultConfigProcessor<ExpertisesConfig> {

    public ExpertiseConfigLoader() {
        super(new ExpertisesConfig(), "expertise/expertises");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ExpertisesConfig config : this.CUSTOM_CONFIGS.values()) {
            ModConfigs.EXPERTISES.tree.skills.addAll(config.tree.skills);
        }
        super.afterConfigsLoad(event);
    }
}