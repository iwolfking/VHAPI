package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.expertises;

import iskallia.vault.config.ExpertisesConfig;
import iskallia.vault.config.ExpertisesGUIConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;

import java.util.HashMap;

public class ExpertisesGUIConfigLoader extends VaultConfigDataLoader<ExpertisesGUIConfig> {
    public ExpertisesGUIConfigLoader(String namespace) {
        super(new ExpertisesGUIConfig(), "expertise/expertise_gui", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ExpertisesGUIConfig config : this.CUSTOM_CONFIGS.values()) {
            ModConfigs.EXPERTISES_GUI.getStyles().putAll(config.getStyles());
        }
    }
}
