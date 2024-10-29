package xyz.iwolfking.vhapi.api.loaders.skills.descriptions;

import com.google.gson.JsonElement;
import iskallia.vault.config.SkillDescriptionsConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.SkillDescriptionsConfigAccessor;

public class SkillDescriptionsConfigLoader extends VaultConfigProcessor<SkillDescriptionsConfig> {
    public SkillDescriptionsConfigLoader() {
        super(new SkillDescriptionsConfig(), "skill/descriptions");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(SkillDescriptionsConfig config : this.CUSTOM_CONFIGS.values()) {
            for(JsonElement element : ((SkillDescriptionsConfigAccessor)config).getDescriptions().values()) {
                ModConfigs.COLORS.replaceColorStrings(element);
            }

            ((SkillDescriptionsConfigAccessor)ModConfigs.SKILL_DESCRIPTIONS).getDescriptions().putAll(((SkillDescriptionsConfigAccessor) config).getDescriptions());
        }
        super.afterConfigsLoad(event);
    }

}
