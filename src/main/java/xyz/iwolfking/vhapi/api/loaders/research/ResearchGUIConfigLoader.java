package xyz.iwolfking.vhapi.api.loaders.research;

import iskallia.vault.config.ResearchesGUIConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

public class ResearchGUIConfigLoader extends VaultConfigProcessor<ResearchesGUIConfig> {
    public ResearchGUIConfigLoader() {
        super(new ResearchesGUIConfig(), "research/research_styles");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ResourceLocation key : this.CUSTOM_CONFIGS.keySet()) {
            if(key.getPath().contains("remove")) {
                for(String research : CUSTOM_CONFIGS.get(key).getStyles().keySet()) {
                    ModConfigs.RESEARCHES_GUI.getStyles().remove(research);
                }
            }
            else if(key.getPath().contains("researches_gui")) {
                ModConfigs.RESEARCHES_GUI = CUSTOM_CONFIGS.get(key);
            }
            else {
                ModConfigs.RESEARCHES_GUI.getStyles().putAll(CUSTOM_CONFIGS.get(key).getStyles());
            }
        }
        super.afterConfigsLoad(event);
    }
}
