package xyz.iwolfking.vhapi.api.lib.config.loaders.skills;

import iskallia.vault.config.AbilitiesGUIConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.lib.loaders.VaultConfigDataLoader;

import java.util.HashMap;

public class AbilitiesGUIConfigLoader extends VaultConfigDataLoader<AbilitiesGUIConfig> {
    public AbilitiesGUIConfigLoader(String namespace) {
        super(new AbilitiesGUIConfig(), "abilities/ability_gui", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(AbilitiesGUIConfig config : this.CUSTOM_CONFIGS.values()) {
            ModConfigs.ABILITIES_GUI.getStyles().putAll(config.getStyles());
        }
    }
}
