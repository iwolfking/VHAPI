package xyz.iwolfking.vhapi.api.loaders.research.groups;

import iskallia.vault.config.ResearchGroupStyleConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

public class ResearchGroupGUIConfigLoader extends VaultConfigProcessor<ResearchGroupStyleConfig> {
    public ResearchGroupGUIConfigLoader() {
        super(new ResearchGroupStyleConfig(), "research/group_styles");
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
        super.afterConfigsLoad(event);
    }
}
