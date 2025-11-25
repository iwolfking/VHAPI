package xyz.iwolfking.vhapi.api.loaders.skills;

import iskallia.vault.config.SkillScrollConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.SkillScrollConfigAccessor;

public class SkillScrollsConfigLoader extends VaultConfigProcessor<SkillScrollConfig> {
    public SkillScrollsConfigLoader() {
        super(new SkillScrollConfig(), "skill/scrolls");
    }


    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(SkillScrollConfig config : this.CUSTOM_CONFIGS.values()) {
            ((SkillScrollConfigAccessor)ModConfigs.SKILL_SCROLLS).getAbilities().addAll(((SkillScrollConfigAccessor)config).getAbilities());
            ((SkillScrollConfigAccessor)ModConfigs.SKILL_SCROLLS).getTalents().addAll(((SkillScrollConfigAccessor)config).getTalents());
            ((SkillScrollConfigAccessor)ModConfigs.SKILL_SCROLLS).getModifiers().addAll(((SkillScrollConfigAccessor)config).getModifiers());
            super.afterConfigsLoad(event);
        }
    }
}
