package xyz.iwolfking.vhapi.api.loaders.skills;

import iskallia.vault.config.AbilitiesGUIConfig;
import iskallia.vault.config.PrestigePowersConfig;
import iskallia.vault.config.PrestigePowersGUIConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

public class PrestigePowersGUIConfigLoader extends VaultConfigProcessor<PrestigePowersGUIConfig> {
    public PrestigePowersGUIConfigLoader() {
        super(new PrestigePowersGUIConfig(), "prestige/prestige_gui");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ResourceLocation key : this.CUSTOM_CONFIGS.keySet()) {
            if(key.getPath().contains("remove")) {
                for(String ability : CUSTOM_CONFIGS.get(key).getStyles().keySet()) {
                    ModConfigs.PRESTIGE_POWERS_GUI.getStyles().remove(ability);
                }
            }
            else if(key.getPath().contains("overwrite")) {
                ModConfigs.PRESTIGE_POWERS_GUI = CUSTOM_CONFIGS.get(key);
            }
            else {
                ModConfigs.PRESTIGE_POWERS_GUI.getStyles().putAll(CUSTOM_CONFIGS.get(key).getStyles());
            }

        }
        super.afterConfigsLoad(event);
    }
}
