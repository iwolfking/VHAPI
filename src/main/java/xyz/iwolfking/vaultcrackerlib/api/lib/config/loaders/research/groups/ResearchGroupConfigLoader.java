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
    public ResearchGroupConfigLoader(String namespace) {
        super(new ResearchGroupConfig(), "research/groups", new HashMap<>(), namespace);
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
