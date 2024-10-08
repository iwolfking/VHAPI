package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.talents;

import iskallia.vault.config.ExpertisesGUIConfig;
import iskallia.vault.config.TalentsGUIConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;

import java.util.HashMap;

public class TalentsGUIConfigLoader extends VaultConfigDataLoader<TalentsGUIConfig> {
    public TalentsGUIConfigLoader(String namespace) {
        super(new TalentsGUIConfig(), "talents/talent_gui", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(TalentsGUIConfig config : this.CUSTOM_CONFIGS.values()) {
            ModConfigs.TALENTS_GUI.getStyles().putAll(config.getStyles());
        }
    }
}
