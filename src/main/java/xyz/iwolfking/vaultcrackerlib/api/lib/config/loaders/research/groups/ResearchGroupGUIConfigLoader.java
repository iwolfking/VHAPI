package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.research.groups;

import iskallia.vault.config.ResearchGroupConfig;
import iskallia.vault.config.ResearchGroupStyleConfig;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.research.group.ResearchGroup;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;

import java.util.HashMap;
import java.util.Map;

public class ResearchGroupGUIConfigLoader extends VaultConfigDataLoader<ResearchGroupStyleConfig> {
    public ResearchGroupGUIConfigLoader(String namespace) {
        super(new ResearchGroupStyleConfig(), "research/group_styles", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ResourceLocation key : this.CUSTOM_CONFIGS.keySet()) {
            if(key.getPath().contains("remove")) {
                for(String groupKey : CUSTOM_CONFIGS.get(key).getStyles().keySet()) {
                    if(ModConfigs.RESEARCH_GROUP_STYLES.getStyles().containsKey(groupKey)) {
                        ModConfigs.RESEARCH_GROUP_STYLES.getStyles().remove(groupKey);
                    }
                }
            }
            else if(key.getPath().contains("overwrite")) {
                ModConfigs.RESEARCH_GROUP_STYLES = CUSTOM_CONFIGS.get(key);
            }
            else {
                ModConfigs.RESEARCH_GROUP_STYLES.getStyles().putAll(CUSTOM_CONFIGS.get(key).getStyles());
            }

        }
    }
}
