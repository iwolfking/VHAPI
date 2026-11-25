package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.PrestigePowersConfig;
import iskallia.vault.gear.attribute.VaultGearAttribute;
import iskallia.vault.skill.base.LearnableSkill;
import iskallia.vault.skill.base.TieredSkill;
import iskallia.vault.skill.prestige.*;
import iskallia.vault.skill.prestige.core.ActivatableTrinketPrestigePower;
import iskallia.vault.skill.prestige.core.PrestigePower;
import iskallia.vault.skill.prestige.core.TrinketPrestigePower;
import iskallia.vault.skill.tree.PrestigeTree;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.IntStream;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.*;

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
        private final PrestigeTree tree = new PrestigeTree();
        private PrestigePowersConfig.Ladder ladder = new PrestigePowersConfig.Ladder();

        public Builder() {
            super(PrestigePowersConfig::new);
        }

        public Builder configureLadder(List<Integer> tierPrices, int stepPercentPerTierOwned, int unlockLevel, boolean requireHerald) {
            PrestigePowersConfigLadderAccessor accessor = (PrestigePowersConfigLadderAccessor) this.ladder;
            accessor.setTierPrices(new ArrayList<>(tierPrices));
            accessor.setStepPercentPerTierOwned(stepPercentPerTierOwned);
            accessor.setUnlockLevel(unlockLevel);
            accessor.setRequireHerald(requireHerald);
            return this;
        }

        private void assignSkillValues(LearnableSkill skill, String id, String name, int learnPointCost, int knowledgeCost, int unlockLevel, int regretCost, int tier, int tierLock) {
            skill.setId(id);
            skill.setName(name);
            ((LearnableSkillAccessor) skill).setLearnCost(learnPointCost);
            ((LearnableSkillAccessor) skill).setUnlockLevel(unlockLevel);
            ((LearnableSkillAccessor) skill).setRegretCost(regretCost);
            ((PrestigePowerAccessor) skill).setKnowledgeCost(knowledgeCost);
            ((PrestigePowerAccessor) skill).setTier(tier);
            ((PrestigePowerAccessor) skill).setTierLock(tierLock);
        }

        private void assignSkillValues(TieredSkill skill, String id, String name) {
            skill.setId(id);
            skill.setName(name);
        }

        public Builder add(String id, String name, int pointCost, int knowledgeCost, int unlockLevel, int regretCost, int tier, int tierLock, int numberOfTiers, IntFunction<? extends PrestigePower> tierFactory) {
            TieredSkill power = new TieredSkill(unlockLevel, pointCost, 1, IntStream.range(0, numberOfTiers).mapToObj(tierFactory));
            for (int i = 0; i < power.getTiers().size(); i++) {
                String subId = numberOfTiers > 1 ? id.toLowerCase() + "_" + (i + 1) : id.toLowerCase() + "_1";
                assignSkillValues(power.getChild(i + 1), subId, name, pointCost, knowledgeCost, unlockLevel, regretCost, tier, i == 0 ? tierLock : 0);
            }

            assignSkillValues(power, id, name);
            tree.skills.add(power);
            return this;
        }

        public Builder addSingleTier(String id, String name, int pointCost, int knowledgeCost, int unlockLevel, int regretCost, int tier, int tierLock, PrestigePower powerInstance) {
            return add(id, name, pointCost, knowledgeCost, unlockLevel, regretCost, tier, tierLock, 1, index -> powerInstance);
        }


        public Builder addGearAttributeBooleanPower(String id, String name, VaultGearAttribute<Boolean> attribute, boolean value, int tier, int regretCost) {
            return addSingleTier(id, name, 0, 0, 0, regretCost, tier, 0, new GearAttributeBooleanPrestigePower(0, 0, 0, regretCost, attribute, value));
        }

        public Builder addGearAttributePower(String id, String name, VaultGearAttribute<? extends Number> attribute, double value, int tier, int regretCost) {
            return addSingleTier(id, name, 0, 0, 0, regretCost, tier, 0, new GearAttributePrestigePower(0, 0, 0, regretCost, attribute, value));
        }

        public Builder addNoUsesPower(String id, String name, Item item, int tier, int regretCost) {
            return addSingleTier(id, name, 0, 0, 0, regretCost, tier, 0, new NoVaultUsesPrestigePower());
        }

        public Builder addGearDurabilityPower(String id, String name, float damageReduction, int tier, int regretCost) {
            return addSingleTier(id, name, 0, 0, 0, regretCost, tier, 0, new GearDurabilityPrestigePower(damageReduction));
        }

        public Builder addToolDurabilityPower(String id, String name, float damageReduction, int tier, int regretCost) {
            return addSingleTier(id, name, 0, 0, 0, regretCost, tier, 0, new ToolDurabilityPrestigePower(damageReduction));
        }

        public Builder addBlackMarketRerollsPower(String id, String name, int costMultiplier, int tier, int regretCost) {
            return addSingleTier(id, name, 0, 0, 0, regretCost, tier, 0, new BlackMarketRerollsPrestigePowerPower(costMultiplier));
        }

        public Builder addActivatableTrinketPower(String id, String name, ResourceLocation trinketEffectId, int tier, int regretCost) {
            return addSingleTier(id, name, 0, 0, 0, regretCost, tier, 0, new ActivatableTrinketPrestigePower(trinketEffectId));
        }

        public Builder addTrinketPower(String id, String name, ResourceLocation trinketEffectId, int tier, int regretCost) {
            return addSingleTier(id, name, 0, 0, 0, regretCost, tier, 0, new TrinketPrestigePower(trinketEffectId));
        }

        public Builder addInstantItemUsePower(String id, String name, int tier, int regretCost) {
            return addSingleTier(id, name, 0, 0, 0, regretCost, tier, 0, new InstantItemUsePrestigePower());
        }

        public Builder addBlackMarketerPower(String id, String name, int tier, int regretCost) {
            return addSingleTier(id, name, 0, 0, 0, regretCost, tier, 0, new BlackMarketerPrestigePower());
        }

        public Builder addMagnetMasteryPower(String id, String name, int tier, int regretCost) {
            return addSingleTier(id, name, 0, 0, 0, regretCost, tier, 0, new MagnetMasteryPrestigePower());
        }

        public Builder addCrateTierPower(String id, String name, int extraTier, int tier, int regretCost) {
            return addSingleTier(id, name, 0, 0, 0, regretCost, tier, 0, new CrateTierPrestigePower(extraTier));
        }

        public Builder addTemporalShardChancePower(String id, String name, float chance, int tier, int regretCost) {
            return addSingleTier(id, name, 0, 0, 0, regretCost, tier, 0, new TemporalShardChancePrestigePower(chance));
        }

        public Builder addTreasureHunterPower(String id, String name, float bonusPerStack, int maxStacks, int tier, int regretCost) {
            return addSingleTier(id, name, 0, 0, 0, regretCost, tier, 0, new TreasureHunterPrestigePower(bonusPerStack, maxStacks));
        }

        public Builder addWingsPower(String id, String name, int tier, int regretCost) {
            return addSingleTier(id, name, 0, 0, 0, regretCost, tier, 0, new WingsActivationPrestigePower());
        }

        public Builder addSuperCrystalsPower(String id, String name, int extraCapacity, int tier, int regretCost) {
            return addSingleTier(id, name, 0, 0, 0, regretCost, tier, 0, new SuperCrystalsPrestigePower(extraCapacity));
        }

        public Builder addChainMinerPower(String id, String name, int tier, int regretCost) {
            return addSingleTier(id, name, 0, 0, 0, regretCost, tier, 0, new ChainMinerPrestigePower());
        }

        public Builder addPotionEffectPower(String id, String name, MobEffect effect, int amplifier, int tier, int regretCost) {
            return addSingleTier(id, name, 0, 0, 0, regretCost, tier, 0, new PotionEffectPrestigePower(effect.getRegistryName(), amplifier));
        }

        public Builder addCommanderPower(String id, String name, float durationMultiplier, int tier, int regretCost) {
            return addSingleTier(id, name, 0, 0, 0, regretCost, tier, 0, new CommanderPrestigePower(durationMultiplier));
        }

        @Override
        protected void configureConfig(PrestigePowersConfig config) {
            PrestigePowersConfigAccessor accessor = (PrestigePowersConfigAccessor) config;
            accessor.setTree(this.tree);
            accessor.setLadder(this.ladder);
        }
    }
}