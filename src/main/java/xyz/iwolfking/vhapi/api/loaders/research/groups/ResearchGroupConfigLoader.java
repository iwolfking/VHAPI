package xyz.iwolfking.vhapi.api.loaders.research.groups;

import iskallia.vault.config.ResearchGroupConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

import java.util.ArrayList;
import java.util.List;

public class ResearchGroupConfigLoader extends VaultConfigProcessor<ResearchGroupConfig> {
    public ResearchGroupConfigLoader() {
        super(new ResearchGroupConfig(), "research/groups");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ResourceLocation key : this.CUSTOM_CONFIGS.keySet()) {
            ResearchGroupConfig config = CUSTOM_CONFIGS.get(key);
            if(key.getPath().contains("overwrite")) {
                ModConfigs.RESEARCH_GROUPS = config;
            }
            else if (key.getPath().contains("remove")) {
                for (String groupKey : config.getGroups().keySet()) {
                    if (ModConfigs.RESEARCH_GROUPS.getGroups().containsKey(groupKey)) {
                        for (String research : ModConfigs.RESEARCH_GROUPS.getGroups().get(groupKey).getResearch()) {
                            ModConfigs.RESEARCH_GROUPS.getGroups().get(groupKey).getResearch().remove(research);
                        }
                    }
                }
            }
            else {
                for(String groupKey : config.getGroups().keySet()) {
                    if(ModConfigs.RESEARCH_GROUPS.getGroups().containsKey(groupKey)) {
                        List<String> researchToAdd = new ArrayList<>();
                        researchToAdd.addAll(ModConfigs.RESEARCH_GROUPS.getGroups().get(groupKey).getResearch());
                        ModConfigs.RESEARCH_GROUPS.getGroups().get(groupKey).getResearch().addAll(researchToAdd);
                    }
                    else {
                        ModConfigs.RESEARCH_GROUPS.getGroups().put(groupKey, config.getGroups().get(groupKey));
                    }
                }
            }

        }
    }
}
