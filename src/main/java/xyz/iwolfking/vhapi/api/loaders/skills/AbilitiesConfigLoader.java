package xyz.iwolfking.vhapi.api.loaders.skills;

import iskallia.vault.config.AbilitiesConfig;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.skill.base.LearnableSkill;
import iskallia.vault.skill.base.Skill;
import iskallia.vault.skill.base.SpecializedSkill;
import iskallia.vault.skill.tree.AbilityTree;
import iskallia.vault.skill.tree.SkillTree;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.AbilitiesConfigAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.LearnableSkillAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.SpecializedSkillAccessor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class AbilitiesConfigLoader extends VaultConfigProcessor<AbilitiesConfig> {
    public AbilitiesConfigLoader() {
        super(new AbilitiesConfig(), "abilities/abilities");
    }


    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {

        for(AbilitiesConfig config : this.CUSTOM_CONFIGS.values()) {
            ModConfigs.ABILITIES.tree.skills.addAll(config.tree.skills);
        }

        super.afterConfigsLoad(event);
    }

}
