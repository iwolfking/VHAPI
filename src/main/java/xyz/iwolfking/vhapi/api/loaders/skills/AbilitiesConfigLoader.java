package xyz.iwolfking.vhapi.api.loaders.skills;

import iskallia.vault.config.AbilitiesConfig;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.skill.base.LearnableSkill;
import iskallia.vault.skill.base.Skill;
import iskallia.vault.skill.base.SpecializedSkill;
import iskallia.vault.skill.tree.AbilityTree;
import iskallia.vault.skill.tree.SkillTree;
import oshi.util.tuples.Pair;
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

        this.CUSTOM_CONFIGS.forEach((resourceLocation, abilitiesConfig) -> {
            if(resourceLocation.getPath().contains("remove")) {
                List<Skill> skillsToRemove = new ArrayList<>();
                abilitiesConfig.tree.skills.forEach(skill -> {
                    if(ModConfigs.ABILITIES.getAbilityById(skill.getId()).isPresent()) {
                        skillsToRemove.add(ModConfigs.ABILITIES.getAbilityById(skill.getId()).get());
                    }
                });
                ModConfigs.ABILITIES.tree.skills.removeAll(skillsToRemove);
            }
            if(resourceLocation.getPath().contains("replace")) {
                List<Skill> skillsToReplace = new ArrayList<>();
                abilitiesConfig.tree.skills.forEach(skill -> {
                    if(ModConfigs.ABILITIES.getAbilityById(skill.getId()).isPresent()) {
                        skillsToReplace.add(ModConfigs.ABILITIES.getAbilityById(skill.getId()).get());
                    }
                });
                ModConfigs.ABILITIES.tree.skills.forEach(skill -> {
                    if(skillsToReplace.contains(skill)) {
                        if(skill instanceof SpecializedSkill specializedSkill) {
                            ((SpecializedSkillAccessor)specializedSkill).setSpecializations(((SpecializedSkillAccessor)abilitiesConfig.getAbilityById(skill.getId()).get()).getSpecializations());
                        }
                    }
                });
            }
            if(resourceLocation.getPath().contains("add_specialization")) {
                List<Skill> skillsToReplace = new ArrayList<>();
                abilitiesConfig.tree.skills.forEach(skill -> {
                    if(ModConfigs.ABILITIES.getAbilityById(skill.getId()).isPresent()) {
                        skillsToReplace.add(ModConfigs.ABILITIES.getAbilityById(skill.getId()).get());
                    }
                });
                ModConfigs.ABILITIES.tree.skills.forEach(skill -> {
                    if(skillsToReplace.contains(skill)) {
                        if(skill instanceof SpecializedSkill specializedSkill) {
                            List<LearnableSkill> specs = ((SpecializedSkillAccessor)specializedSkill).getSpecializations();
                            Skill newSkill = abilitiesConfig.getAbilityById(specializedSkill.getId()).orElse(null);
                            if(newSkill instanceof SpecializedSkill newSpecializationSkill) {
                                List<LearnableSkill> newSpecs = ((SpecializedSkillAccessor)newSpecializationSkill).getSpecializations();
                                specs.removeIf(learnableSkill -> {
                                    for(LearnableSkill skill1 : newSpecs) {
                                        if(learnableSkill.getId().equals(skill1.getId())) {
                                            return true;
                                        }
                                    }

                                    return false;
                                });
                                specs.addAll(newSpecs);
                            }
                            ((SpecializedSkillAccessor)specializedSkill).setSpecializations(specs);
                        }
                    }
                });
            }
            else {
                ModConfigs.ABILITIES.tree.skills.addAll(abilitiesConfig.tree.skills);
            }
        });


        super.afterConfigsLoad(event);
    }

}
