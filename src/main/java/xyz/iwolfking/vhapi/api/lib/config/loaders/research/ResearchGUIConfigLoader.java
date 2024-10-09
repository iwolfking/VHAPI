package xyz.iwolfking.vhapi.api.lib.config.loaders.research;

import iskallia.vault.config.ResearchesGUIConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.lib.loaders.VaultConfigDataLoader;

import java.util.HashMap;

public class ResearchGUIConfigLoader extends VaultConfigDataLoader<ResearchesGUIConfig> {
    public ResearchGUIConfigLoader(String namespace) {
        super(new ResearchesGUIConfig(), "research/research_styles", new HashMap<>(), namespace);
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
    }
}
