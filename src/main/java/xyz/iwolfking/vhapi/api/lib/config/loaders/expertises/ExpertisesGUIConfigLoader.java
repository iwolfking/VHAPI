package xyz.iwolfking.vhapi.api.lib.config.loaders.expertises;

import iskallia.vault.config.ExpertisesGUIConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.lib.loaders.VaultConfigDataLoader;

import java.util.HashMap;

public class ExpertisesGUIConfigLoader extends VaultConfigDataLoader<ExpertisesGUIConfig> {
    public ExpertisesGUIConfigLoader(String namespace) {
        super(new ExpertisesGUIConfig(), "expertise/expertise_gui", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ResourceLocation key : this.CUSTOM_CONFIGS.keySet()) {
            if(key.getPath().contains("remove")) {
                for(String ability : CUSTOM_CONFIGS.get(key).getStyles().keySet()) {
                    ModConfigs.EXPERTISES_GUI.getStyles().remove(ability);
                }
            }
            else if(key.getPath().contains("overwrite")) {
                ModConfigs.EXPERTISES_GUI = CUSTOM_CONFIGS.get(key);
            }
            else {
                ModConfigs.EXPERTISES_GUI.getStyles().putAll(CUSTOM_CONFIGS.get(key).getStyles());
            }

        }
    }
}
