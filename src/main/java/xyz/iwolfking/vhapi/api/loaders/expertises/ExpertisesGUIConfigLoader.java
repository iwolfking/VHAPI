package xyz.iwolfking.vhapi.api.loaders.expertises;

import iskallia.vault.config.ExpertisesGUIConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

public class ExpertisesGUIConfigLoader extends VaultConfigProcessor<ExpertisesGUIConfig> {
    public ExpertisesGUIConfigLoader() {
        super(new ExpertisesGUIConfig(), "expertise/expertise_gui");
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
        super.afterConfigsLoad(event);
    }
}
