package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.PrestigePowersConfig;
import iskallia.vault.gear.attribute.VaultGearAttribute;
import iskallia.vault.skill.base.LearnableSkill;
import iskallia.vault.skill.base.TieredSkill;
import iskallia.vault.skill.prestige.*;
import iskallia.vault.skill.prestige.core.ActivatableTrinketPrestigePower;
import iskallia.vault.skill.prestige.core.PrestigePower;
import iskallia.vault.skill.tree.PrestigeTree;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.*;

import java.util.function.IntFunction;
import java.util.stream.IntStream;

public abstract class AbstractPrestigePowerProvider extends AbstractVaultConfigDataProvider<AbstractPrestigePowerProvider.Builder> {
    protected AbstractPrestigePowerProvider(DataGenerator generator, String modid) {
        super(generator, modid, "prestige/powers", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Prestige Powers Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<PrestigePowersConfig> {
        PrestigeTree tree = new PrestigeTree();

        public Builder() {
            super(PrestigePowersConfig::new);
        }

        private void assignSkillValues(LearnableSkill skill, String id, String name, int learnPointCost, int knowledgeCost, int unlockLevel, int regretCost, int tier, int tierLock) {
            skill.setId(id);
            skill.setName(name);
            ((LearnableSkillAccessor)skill).setLearnCost(learnPointCost);
            ((LearnableSkillAccessor)skill).setUnlockLevel(unlockLevel);
            ((LearnableSkillAccessor)skill).setRegretCost(regretCost);
            ((PrestigePowerAccessor)skill).setKnowledgeCost(knowledgeCost);
            ((PrestigePowerAccessor)skill).setTier(tier);
            ((PrestigePowerAccessor)skill).setTierLock(tierLock);
        }

        private void assignSkillValues(TieredSkill skill, String id, String name) {
            skill.setId(id);
            skill.setName(name);
        }

        public Builder add(String id, String name, int pointCost, int knowledgeCost, int unlockLevel, int regretCost, int tier, int tierLock, int numberOfTiers, IntFunction<? extends PrestigePower> tierFactory) {
            TieredSkill power = new TieredSkill(unlockLevel, pointCost, 1, IntStream.range(0, numberOfTiers).mapToObj(tierFactory));
            for(int i = 0; i < power.getTiers().size(); i++) {
                assignSkillValues(power.getChild(i + 1), id.toLowerCase() + "_" + i, name, pointCost, knowledgeCost, unlockLevel, regretCost, tier, i == 0 ? tierLock : 0);
            }

            assignSkillValues(power, id, name);

            tree.skills.add(power);

            return this;
        }

        @Override
        protected void configureConfig(PrestigePowersConfig config) {
            config.tree = tree;
        }

    }
}
