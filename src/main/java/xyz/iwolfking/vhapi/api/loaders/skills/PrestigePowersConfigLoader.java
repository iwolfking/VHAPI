package xyz.iwolfking.vhapi.api.loaders.skills;

import iskallia.vault.config.PrestigePowersConfig;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.skill.base.Skill;
import iskallia.vault.skill.base.TieredSkill;
import iskallia.vault.skill.tree.PrestigeTree;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.PrestigePowersConfigAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.PrestigePowersConfigLadderAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.TieredSkillAccessor;

public class PrestigePowersConfigLoader extends VaultConfigProcessor<PrestigePowersConfig> {
    public PrestigePowersConfigLoader() {
        super(new PrestigePowersConfig(), "prestige/powers");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        PrestigePowersConfig baseConfig = ModConfigs.PRESTIGE_POWERS;
        PrestigePowersConfigAccessor baseAccessor = (PrestigePowersConfigAccessor) baseConfig;

        this.CUSTOM_CONFIGS.forEach((resourceLocation, prestigeConfig) -> {
            PrestigePowersConfigAccessor customAccessor = (PrestigePowersConfigAccessor) prestigeConfig;

            if (customAccessor.getLadder() != null) {
                PrestigePowersConfigLadderAccessor baseLadder = (PrestigePowersConfigLadderAccessor) baseAccessor.getLadder();
                PrestigePowersConfigLadderAccessor customLadder = (PrestigePowersConfigLadderAccessor) customAccessor.getLadder();

                if (baseLadder != null && customLadder != null) {
                    if (customLadder.getTierPrices() != null && !customLadder.getTierPrices().isEmpty()) {
                        baseLadder.setTierPrices(new ArrayList<>(customLadder.getTierPrices()));
                    }
                    if (customLadder.getStepPercentPerTierOwned() > 0) {
                        baseLadder.setStepPercentPerTierOwned(customLadder.getStepPercentPerTierOwned());
                    }
                    if (customLadder.getUnlockLevel() > 0) {
                        baseLadder.setUnlockLevel(customLadder.getUnlockLevel());
                    }
                    baseLadder.setRequireHerald(customLadder.getRequireHerald());
                } else if (customLadder != null) {
                    baseAccessor.setLadder(((PrestigePowersConfigAccessor)prestigeConfig).getLadder());
                }
            }

            if (prestigeConfig.tree != null && prestigeConfig.tree.skills != null) {
                if (resourceLocation.getPath().contains("remove")) {
                    List<Skill> skillsToRemove = new ArrayList<>();
                    prestigeConfig.tree.skills.forEach(skill -> {
                        getSkillById(baseConfig.tree, skill.getId()).ifPresent(skillsToRemove::add);
                    });
                    baseConfig.tree.skills.removeAll(skillsToRemove);
                } else if (resourceLocation.getPath().contains("replace")) {
                    List<Skill> skillsToReplace = new ArrayList<>();
                    prestigeConfig.tree.skills.forEach(skill -> {
                        getSkillById(baseConfig.tree, skill.getId()).ifPresent(skillsToReplace::add);
                    });
                    baseConfig.tree.skills.forEach(skill -> {
                        if (skillsToReplace.contains(skill) && skill instanceof TieredSkill tieredSkill) {
                            getSkillById(prestigeConfig.tree, skill.getId()).ifPresent(newSkill -> {
                                if (newSkill instanceof TieredSkill newTieredSkill) {
                                    ((TieredSkillAccessor) tieredSkill).setTiers(newTieredSkill.getTiers());
                                }
                            });
                        }
                    });
                } else {
                    baseConfig.tree.skills.addAll(prestigeConfig.tree.skills);
                }
            }
        });

        super.afterConfigsLoad(event);
    }

    private Optional<Skill> getSkillById(PrestigeTree tree, String id) {
        if (tree == null || tree.skills == null || id == null) {
            return Optional.empty();
        }
        for (Skill skill : tree.skills) {
            if (id.equals(skill.getId())) {
                return Optional.of(skill);
            }
        }
        return Optional.empty();
    }
}