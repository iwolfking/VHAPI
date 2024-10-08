package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.skills;

import iskallia.vault.config.AbilitiesConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;

import java.util.HashMap;

public class AbilitiesConfigLoader extends VaultConfigDataLoader<AbilitiesConfig> {
    public AbilitiesConfigLoader(String namespace) {
        super(new AbilitiesConfig(), "abilities/ability", new HashMap<>(), namespace);
    }


    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(AbilitiesConfig config : this.CUSTOM_CONFIGS.values()) {
            ModConfigs.ABILITIES.tree.skills.addAll(config.tree.skills);
        }
    }
}
