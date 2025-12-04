package xyz.iwolfking.vhapi.api.loaders.talents;

import iskallia.vault.config.TalentsConfig;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.skill.base.Skill;
import iskallia.vault.skill.base.TieredSkill;
import iskallia.vault.skill.tree.ExpertiseTree;
import iskallia.vault.skill.tree.TalentTree;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.TieredSkillAccessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TalentConfigLoader extends VaultConfigProcessor<TalentsConfig> {
    public TalentConfigLoader() {
        super(new TalentsConfig(), "talents/talents");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        this.CUSTOM_CONFIGS.forEach((resourceLocation, talentsConfig) -> {
            if(resourceLocation.getPath().contains("remove")) {
                List<Skill> skillsToRemove = new ArrayList<>();
                talentsConfig.tree.skills.forEach(skill -> {
                    if(getSkillById(ModConfigs.TALENTS.tree, skill.getId()).isPresent()) {
                        skillsToRemove.add(getSkillById(ModConfigs.TALENTS.tree, skill.getId()).get());
                    }
                });
                ModConfigs.TALENTS.tree.skills.removeAll(skillsToRemove);
            }
            if(resourceLocation.getPath().contains("replace")) {
                List<Skill> skillsToReplace = new ArrayList<>();
                talentsConfig.tree.skills.forEach(skill -> {
                    if(getSkillById(ModConfigs.TALENTS.tree, skill.getId()).isPresent()) {
                        skillsToReplace.add(getSkillById(ModConfigs.TALENTS.tree, skill.getId()).get());
                    }
                });
                ModConfigs.TALENTS.tree.skills.forEach(skill -> {
                    if(skillsToReplace.contains(skill)) {
                        if(skill instanceof TieredSkill tieredSkill) {
                            TieredSkill newTieredSkill = (TieredSkill) getSkillById(talentsConfig.tree, skill.getId()).get();
                            ((TieredSkillAccessor)tieredSkill).setTiers(newTieredSkill.getTiers());
                            ((TieredSkillAccessor)tieredSkill).setMaxLearnableTier(newTieredSkill.getMaxLearnableTier());
                        }
                    }
                });
            }
            else  {
                ModConfigs.TALENTS.tree.skills.addAll(talentsConfig.tree.skills);
            }
        });

        super.afterConfigsLoad(event);
    }

    private Optional<Skill> getSkillById(TalentTree tree, String id) {
        for(Skill skill : tree.skills) {
            if(skill.getId().equals(id)) {
                return Optional.of(skill);
            }
        }

        return Optional.empty();
    }
}
