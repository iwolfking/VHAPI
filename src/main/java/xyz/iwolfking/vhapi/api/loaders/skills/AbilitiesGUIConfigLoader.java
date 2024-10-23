package xyz.iwolfking.vhapi.api.loaders.skills;

import iskallia.vault.config.AbilitiesGUIConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

public class AbilitiesGUIConfigLoader extends VaultConfigProcessor<AbilitiesGUIConfig> {
    public AbilitiesGUIConfigLoader() {
        super(new AbilitiesGUIConfig(), "abilities/ability_gui");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ResourceLocation key : this.CUSTOM_CONFIGS.keySet()) {
            if(key.getPath().contains("remove")) {
                for(String ability : CUSTOM_CONFIGS.get(key).getStyles().keySet()) {
                    ModConfigs.ABILITIES_GUI.getStyles().remove(ability);
                }
            }
            else if(key.getPath().contains("overwrite")) {
                ModConfigs.ABILITIES_GUI = CUSTOM_CONFIGS.get(key);
            }
            else {
                ModConfigs.ABILITIES_GUI.getStyles().putAll(CUSTOM_CONFIGS.get(key).getStyles());
            }

        }
    }
}
