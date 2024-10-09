package xyz.iwolfking.vhapi.api.lib.config.loaders.skills;

import iskallia.vault.config.AbilitiesGUIConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.lib.loaders.VaultConfigDataLoader;

import java.util.HashMap;

public class AbilitiesGUIConfigLoader extends VaultConfigDataLoader<AbilitiesGUIConfig> {
    public AbilitiesGUIConfigLoader(String namespace) {
        super(new AbilitiesGUIConfig(), "abilities/ability_gui", new HashMap<>(), namespace);
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
