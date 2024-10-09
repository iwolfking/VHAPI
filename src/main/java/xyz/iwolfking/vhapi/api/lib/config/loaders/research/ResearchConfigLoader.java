package xyz.iwolfking.vhapi.api.lib.config.loaders.research;

import iskallia.vault.config.ResearchConfig;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.research.type.CustomResearch;
import iskallia.vault.research.type.ModResearch;
import net.minecraftforge.fml.common.Mod;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.lib.loaders.VaultConfigDataLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = "vhapi", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ResearchConfigLoader extends VaultConfigDataLoader<ResearchConfig> {

    public static final ResearchConfig instance = new ResearchConfig();
    public ResearchConfigLoader(String namespace) {
        super(new ResearchConfig(), "research/researches", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ResearchConfig config : this.CUSTOM_CONFIGS.values()) {
            List<ModResearch> researchesToRemove = new ArrayList<>();
            List<CustomResearch> customResearchesToRemove = new ArrayList<>();
            for(ModResearch research: config.MOD_RESEARCHES) {
                for(ModResearch originalResearch : ModConfigs.RESEARCHES.MOD_RESEARCHES) {
                    if(originalResearch.getName().equals(research.getName())) {
                        researchesToRemove.add(originalResearch);
                    }
                }
            }
            for(CustomResearch research: config.CUSTOM_RESEARCHES) {
                for(CustomResearch originalResearch : ModConfigs.RESEARCHES.CUSTOM_RESEARCHES) {
                    if(originalResearch.getName().equals(research.getName())) {
                        customResearchesToRemove.add(originalResearch);
                    }
                }
            }

            ModConfigs.RESEARCHES.MOD_RESEARCHES.removeAll(researchesToRemove);
            ModConfigs.RESEARCHES.CUSTOM_RESEARCHES.removeAll(customResearchesToRemove);
            ModConfigs.RESEARCHES.MOD_RESEARCHES.addAll(config.MOD_RESEARCHES);
            ModConfigs.RESEARCHES.CUSTOM_RESEARCHES.addAll(config.CUSTOM_RESEARCHES);
        }
    }

}
