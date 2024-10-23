package xyz.iwolfking.vhapi.api.loaders.talents;

import iskallia.vault.config.TalentsGUIConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

public class TalentsGUIConfigLoader extends VaultConfigProcessor<TalentsGUIConfig> {
    public TalentsGUIConfigLoader() {
        super(new TalentsGUIConfig(), "talents/talent_gui");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(TalentsGUIConfig config : this.CUSTOM_CONFIGS.values()) {
            ModConfigs.TALENTS_GUI.getStyles().putAll(config.getStyles());
        }
    }
}
