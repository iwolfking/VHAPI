package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.research.groups;

import iskallia.vault.config.ResearchConfig;
import iskallia.vault.config.ResearchGroupConfig;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.research.group.ResearchGroup;
import iskallia.vault.research.type.CustomResearch;
import iskallia.vault.research.type.ModResearch;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.research.ResearchConfigLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResearchGroupConfigLoader extends VaultConfigDataLoader<ResearchGroupConfig> {
    public ResearchGroupConfigLoader(ResearchGroupConfigLoader instance, String directory, Map<ResourceLocation, ResearchGroupConfigLoader> configMap, String namespace) {
        super(new ResearchGroupConfig(), "research_groups", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ResearchGroupConfig config : this.CUSTOM_CONFIGS.values()) {
            for(String groupKey : config.getGroups().keySet()) {
                if(ModConfigs.RESEARCH_GROUPS.getGroups().containsKey(groupKey)) {
                    for(String research  : ModConfigs.RESEARCH_GROUPS.getGroups().get(groupKey).getResearch()) {
                        if(research.startsWith("remove:")) {
                            ModConfigs.RESEARCH_GROUPS.getGroups().get(groupKey).getResearch().remove(research.replaceAll("remove:", ""));
                        }
                        else {
                            ModConfigs.RESEARCH_GROUPS.getGroups().get(groupKey).getResearch().add(research);
                        }
                    }
                }
                else {
                    ModConfigs.RESEARCH_GROUPS.getGroups().put(groupKey, config.getGroups().get(groupKey));
                }
            }
        }
    }
}
