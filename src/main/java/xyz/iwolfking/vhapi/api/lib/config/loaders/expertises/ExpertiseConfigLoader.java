package xyz.iwolfking.vhapi.api.lib.config.loaders.expertises;

import iskallia.vault.config.ExpertisesConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.lib.loaders.VaultConfigDataLoader;

import java.util.HashMap;

public class ExpertiseConfigLoader extends VaultConfigDataLoader<ExpertisesConfig> {

    public ExpertiseConfigLoader(String namespace) {
        super(new ExpertisesConfig(), "expertise/expertises", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ExpertisesConfig config : this.CUSTOM_CONFIGS.values()) {
            ModConfigs.EXPERTISES.tree.skills.addAll(config.tree.skills);
        }
    }
}