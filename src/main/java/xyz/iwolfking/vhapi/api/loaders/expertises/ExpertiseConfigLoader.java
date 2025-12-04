package xyz.iwolfking.vhapi.api.loaders.expertises;

import iskallia.vault.config.ExpertisesConfig;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.skill.base.Skill;
import iskallia.vault.skill.base.TieredSkill;
import iskallia.vault.skill.tree.ExpertiseTree;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.TieredSkillAccessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExpertiseConfigLoader extends VaultConfigProcessor<ExpertisesConfig> {

    public ExpertiseConfigLoader() {
        super(new ExpertisesConfig(), "expertise/expertises");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        this.CUSTOM_CONFIGS.forEach((resourceLocation, expertisesConfig) -> {
            if(resourceLocation.getPath().contains("remove")) {
                List<Skill> skillsToRemove = new ArrayList<>();
                expertisesConfig.tree.skills.forEach(skill -> {
                    if(getSkillById(ModConfigs.EXPERTISES.tree, skill.getId()).isPresent()) {
                        skillsToRemove.add(getSkillById(ModConfigs.EXPERTISES.tree, skill.getId()).get());
                    }
                });
                ModConfigs.EXPERTISES.tree.skills.removeAll(skillsToRemove);
            }
            if(resourceLocation.getPath().contains("replace")) {
                List<Skill> skillsToReplace = new ArrayList<>();
                expertisesConfig.tree.skills.forEach(skill -> {
                    if(getSkillById(ModConfigs.EXPERTISES.tree, skill.getId()).isPresent()) {
                        skillsToReplace.add(getSkillById(ModConfigs.EXPERTISES.tree, skill.getId()).get());
                    }
                });
                ModConfigs.EXPERTISES.tree.skills.forEach(skill -> {
                    if(skillsToReplace.contains(skill)) {
                        if(skill instanceof TieredSkill tieredSkill) {
                            TieredSkill newTieredSkill = (TieredSkill) getSkillById(expertisesConfig.tree, skill.getId()).get();
                            ((TieredSkillAccessor)tieredSkill).setTiers(newTieredSkill.getTiers());
                            ((TieredSkillAccessor)tieredSkill).setMaxLearnableTier(newTieredSkill.getMaxLearnableTier());
                        }
                    }
                });
            }
            else  {
                ModConfigs.EXPERTISES.tree.skills.addAll(expertisesConfig.tree.skills);
            }
        });

        super.afterConfigsLoad(event);
    }

    private Optional<Skill> getSkillById(ExpertiseTree tree, String id) {
        for(Skill skill : tree.skills) {
            if(skill.getId().equals(id)) {
                return Optional.of(skill);
            }
        }

        return Optional.empty();
    }
}