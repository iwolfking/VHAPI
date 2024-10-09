package xyz.iwolfking.vhapi.api.lib.config.loaders.talents;

import iskallia.vault.config.TalentsConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.lib.loaders.VaultConfigDataLoader;

import java.util.HashMap;

public class TalentConfigLoader extends VaultConfigDataLoader<TalentsConfig> {
    public TalentConfigLoader(String namespace) {
        super(new TalentsConfig(), "talents/talent", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(TalentsConfig config : this.CUSTOM_CONFIGS.values()) {
            ModConfigs.TALENTS.tree.skills.addAll(config.tree.skills);
        }
    }
}
