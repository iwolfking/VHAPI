package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.research;

import iskallia.vault.config.ResearchConfig;
import iskallia.vault.config.ResearchesGUIConfig;
import iskallia.vault.config.entry.ResearchGroupStyle;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.research.type.CustomResearch;
import iskallia.vault.research.type.ModResearch;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResearchGUIConfigLoader extends VaultConfigDataLoader<ResearchesGUIConfig> {
    public ResearchGUIConfigLoader(String namespace) {
        super(new ResearchesGUIConfig(), "research/researches_gui", new HashMap<>(), namespace);
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
