package xyz.iwolfking.vhapi.api.loaders.skills;

import iskallia.vault.config.AbilitiesConfig;
import iskallia.vault.config.AbilitiesGroups;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.skill.base.LearnableSkill;
import iskallia.vault.skill.base.Skill;
import iskallia.vault.skill.base.SkillContext;
import iskallia.vault.skill.base.SpecializedSkill;
import iskallia.vault.skill.source.SkillSource;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.AbilitiesGroupsAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.SpecializedSkillAccessor;

import java.util.ArrayList;
import java.util.List;

public class AbilitiesGroupLoader extends VaultConfigProcessor<AbilitiesGroups> {
    public AbilitiesGroupLoader() {
        super(new AbilitiesGroups(), "abilities/group");
    }


    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        this.CUSTOM_CONFIGS.forEach(((resourceLocation, abilitiesGroups) -> {
            if(resourceLocation.getPath().contains("overwrite")) {
                ModConfigs.ABILITIES_GROUPS = abilitiesGroups;
            }
            else {
                ((AbilitiesGroupsAccessor)ModConfigs.ABILITIES_GROUPS).getTypes().forEach((abilityType, strings) -> {
                    strings.addAll(((AbilitiesGroupsAccessor)abilitiesGroups).getTypes().get(abilityType));
                });
            }
        }));

        super.afterConfigsLoad(event);
    }

}
