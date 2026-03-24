package xyz.iwolfking.vhapi.api.loaders.skills;

import iskallia.vault.config.PrestigePowersConfig;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.skill.base.Skill;
import iskallia.vault.skill.base.TieredSkill;
import iskallia.vault.skill.prestige.core.PrestigePower;
import iskallia.vault.skill.tree.PrestigeTree;
import iskallia.vault.skill.tree.TalentTree;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.TieredSkillAccessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PrestigePowersConfigLoader extends VaultConfigProcessor<PrestigePowersConfig> {
    public PrestigePowersConfigLoader() {
        super(new PrestigePowersConfig(), "prestige/powers");
    }


    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        this.CUSTOM_CONFIGS.forEach((resourceLocation, prestigeConfig) -> {
            if(resourceLocation.getPath().contains("remove")) {
                List<Skill> skillsToRemove = new ArrayList<>();
                prestigeConfig.tree.skills.forEach(skill -> {
                    if(getSkillById(ModConfigs.PRESTIGE_POWERS.tree, skill.getId()).isPresent()) {
                        skillsToRemove.add(getSkillById(ModConfigs.PRESTIGE_POWERS.tree, skill.getId()).get());
                    }
                });
                ModConfigs.PRESTIGE_POWERS.tree.skills.removeAll(skillsToRemove);
            }
            if(resourceLocation.getPath().contains("replace")) {
                List<Skill> skillsToReplace = new ArrayList<>();
                prestigeConfig.tree.skills.forEach(skill -> {
                    if(getSkillById(ModConfigs.PRESTIGE_POWERS.tree, skill.getId()).isPresent()) {
                        skillsToReplace.add(getSkillById(ModConfigs.PRESTIGE_POWERS.tree, skill.getId()).get());
                    }
                });
                ModConfigs.PRESTIGE_POWERS.tree.skills.forEach(skill -> {
                    if(skillsToReplace.contains(skill)) {
                        if(skill instanceof TieredSkill tieredSkill) {
                            TieredSkill newTieredSkill = (TieredSkill) getSkillById(prestigeConfig.tree, skill.getId()).get();
                            ((TieredSkillAccessor)tieredSkill).setTiers(newTieredSkill.getTiers());
                        }
                    }
                });
            }
            else  {
                ModConfigs.PRESTIGE_POWERS.tree.skills.addAll(prestigeConfig.tree.skills);
            }
        });

        super.afterConfigsLoad(event);
    }

    private Optional<Skill> getSkillById(PrestigeTree tree, String id) {
        for(Skill skill : tree.skills) {
            if(skill.getId().equals(id)) {
                return Optional.of(skill);
            }
        }

        return Optional.empty();
    }
}
