package xyz.iwolfking.vhapi.api.loaders.research;

import iskallia.vault.config.ResearchConfig;
import net.minecraftforge.fml.common.Mod;
import xyz.iwolfking.vhapi.api.config.ResearchExclusionConfig;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.init.ModConfigs;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = "vhapi", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ResearchExclusionsConfigLoader extends VaultConfigProcessor<ResearchExclusionConfig> {

    public static final ResearchConfig instance = new ResearchConfig();
    public ResearchExclusionsConfigLoader() {
        super(new ResearchExclusionConfig(), "research/exclusions");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ResearchExclusionConfig config : this.CUSTOM_CONFIGS.values()) {
            config.RESEARCH_EXCLUSIONS.forEach((s, strings) -> {
                if(ModConfigs.RESEARCH_EXCLUSIONS.RESEARCH_EXCLUSIONS.containsKey(s)) {
                    ModConfigs.RESEARCH_EXCLUSIONS.RESEARCH_EXCLUSIONS.get(s).addAll(strings);
                }
                else {
                    ModConfigs.RESEARCH_EXCLUSIONS.RESEARCH_EXCLUSIONS.put(s, strings);
                }
            });
        }
        super.afterConfigsLoad(event);
    }


}
