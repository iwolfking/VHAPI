package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.PrestigePowersConfig;
import iskallia.vault.config.TalentsConfig;
import iskallia.vault.core.world.data.entity.EntityPredicate;
import iskallia.vault.gear.VaultGearRarity;
import iskallia.vault.gear.attribute.VaultGearAttribute;
import iskallia.vault.skill.base.LearnableSkill;
import iskallia.vault.skill.base.TieredSkill;
import iskallia.vault.skill.expertise.type.FarmerTwerker;
import iskallia.vault.skill.prestige.*;
import iskallia.vault.skill.prestige.core.ActivatableTrinketPrestigePower;
import iskallia.vault.skill.prestige.core.PrestigePower;
import iskallia.vault.skill.talent.type.*;
import iskallia.vault.skill.talent.type.health.HighHealthGearAttributeTalent;
import iskallia.vault.skill.talent.type.luckyhit.DamageLuckyHitTalent;
import iskallia.vault.skill.talent.type.luckyhit.ManaLeechLuckyHitTalent;
import iskallia.vault.skill.talent.type.luckyhit.SweepingLuckyHitTalent;
import iskallia.vault.skill.talent.type.mana.HighManaGearAttributeTalent;
import iskallia.vault.skill.talent.type.mana.LowManaDamageTalent;
import iskallia.vault.skill.talent.type.onhit.CastOnHitTalent;
import iskallia.vault.skill.talent.type.onhit.DamageOnHitTalent;
import iskallia.vault.skill.talent.type.onhit.EffectOnHitTalent;
import iskallia.vault.skill.talent.type.onkill.CastOnKillTalent;
import iskallia.vault.skill.tree.PrestigeTree;
import iskallia.vault.skill.tree.TalentTree;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.*;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.IntToDoubleFunction;
import java.util.function.Supplier;
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

        private void assignSkillValues(PrestigePower skill, String id, String name, int learnPointCost, int knowledgeCost, int unlockLevel, int regretCost, int tier, int tierLock) {
            skill.setId(id);
            skill.setName(name);
            ((LearnableSkillAccessor)skill).setLearnCost(learnPointCost);
            ((LearnableSkillAccessor)skill).setUnlockLevel(unlockLevel);
            ((LearnableSkillAccessor)skill).setRegretCost(regretCost);
            ((PrestigePowerAccessor)skill).setKnowledgeCost(knowledgeCost);
            ((PrestigePowerAccessor)skill).setTier(tier);
            ((PrestigePowerAccessor)skill).setTierLock(tierLock);
            tree.skills.add(skill);

        }

        public Builder addTrinketPower(String id, String name, int pointCost, int knowledgeCost, int unlockLevel, int tier, int tierLock, ResourceLocation trinketEffectId) {
            PrestigePower power = new ActivatableTrinketPrestigePower(trinketEffectId);
            assignSkillValues(power, id, name, pointCost, knowledgeCost, unlockLevel, 0, tier, tierLock);
            return this;
        }

        public Builder addBerserkPower(String id, String name, int pointCost, int knowledgeCost, int unlockLevel, int tier, int tierLock) {
            PrestigePower power = new BerserkPrestigePower();
            assignSkillValues(power, id, name, pointCost, knowledgeCost, unlockLevel, 0, tier, tierLock);
            return this;
        }

        public Builder addBlackMarketPower(String id, String name, int pointCost, int knowledgeCost, int unlockLevel, int tier, int tierLock, int extraRolls) {
            PrestigePower power = new BlackMarketRerollsPrestigePowerPower(extraRolls);
            assignSkillValues(power, id, name, pointCost, knowledgeCost, unlockLevel, 0, tier, tierLock);
            return this;
        }

        public Builder addBountyCooldownReductionPower(String id, String name, int pointCost, int knowledgeCost, int unlockLevel, int tier, int tierLock, int minutesReduction) {
            PrestigePower power = new BountyCooldownReductionPrestigePower(minutesReduction);
            assignSkillValues(power, id, name, pointCost, knowledgeCost, unlockLevel, 0, tier, tierLock);
            return this;
        }

        public Builder addChampDamagePower(String id, String name, int pointCost, int knowledgeCost, int unlockLevel, int tier, int tierLock) {
            PrestigePower power = new ChampionDamagePrestigePower();
            assignSkillValues(power, id, name, pointCost, knowledgeCost, unlockLevel, 0, tier, tierLock);
            return this;
        }


        public Builder addCommanderPower(String id, String name, int pointCost, int knowledgeCost, int unlockLevel, int tier, int tierLock) {
            PrestigePower power = new CommanderPrestigePower();
            assignSkillValues(power, id, name, pointCost, knowledgeCost, unlockLevel, 0, tier, tierLock);
            return this;
        }

        public Builder addGearAttributeBoolPower(String id, String name, int pointCost, int knowledgeCost, int unlockLevel, int tier, int tierLock, VaultGearAttribute<?> attribute, boolean value) {
            PrestigePower power = new GearAttributeBooleanPrestigePower(unlockLevel, pointCost, knowledgeCost, 0, attribute, value);
            assignSkillValues(power, id, name, pointCost, knowledgeCost, unlockLevel, 0, tier, tierLock);
            return this;
        }

        public Builder addGearAttributeBoolPower(String id, String name, int pointCost, int knowledgeCost, int unlockLevel, int tier, int tierLock, VaultGearAttribute<?> attribute, double value) {
            PrestigePower power = new GearAttributePrestigePower(unlockLevel, pointCost, knowledgeCost, 0, attribute, value);
            assignSkillValues(power, id, name, pointCost, knowledgeCost, unlockLevel, 0, tier, tierLock);
            return this;
        }

        public Builder addGearDurabilityPower(String id, String name, int pointCost, int knowledgeCost, int unlockLevel, int tier, int tierLock, float damageReduction) {
            PrestigePower power = new GearDurabilityPrestigePower(damageReduction);
            assignSkillValues(power, id, name, pointCost, knowledgeCost, unlockLevel, 0, tier, tierLock);
            return this;
        }

        public Builder addInstantItemUsePower(String id, String name, int pointCost, int knowledgeCost, int unlockLevel, int tier, int tierLock) {
            PrestigePower power = new InstantItemUsePrestigePower();
            assignSkillValues(power, id, name, pointCost, knowledgeCost, unlockLevel, 0, tier, tierLock);
            return this;
        }

        public Builder addMagnetMasteryPower(String id, String name, int pointCost, int knowledgeCost, int unlockLevel, int tier, int tierLock) {
            PrestigePower power = new MagnetMasteryPrestigePower();
            assignSkillValues(power, id, name, pointCost, knowledgeCost, unlockLevel, 0, tier, tierLock);
            return this;
        }

        public Builder addMasterfulPower(String id, String name, int pointCost, int knowledgeCost, int unlockLevel, int tier, int tierLock) {
            PrestigePower power = new MasterfulPrestigePower();
            assignSkillValues(power, id, name, pointCost, knowledgeCost, unlockLevel, 0, tier, tierLock);
            return this;
        }

        public Builder addMultiJumpPower(String id, String name, int pointCost, int knowledgeCost, int unlockLevel, int tier, int tierLock) {
            PrestigePower power = new MultiJumpPrestigePower();
            assignSkillValues(power, id, name, pointCost, knowledgeCost, unlockLevel, 0, tier, tierLock);
            return this;
        }

        public Builder addNoVaultUsesPower(String id, String name, int pointCost, int knowledgeCost, int unlockLevel, int tier, int tierLock) {
            PrestigePower power = new NoVaultUsesPrestigePower();
            assignSkillValues(power, id, name, pointCost, knowledgeCost, unlockLevel, 0, tier, tierLock);
            return this;
        }

        public Builder addWardPower(String id, String name, int pointCost, int knowledgeCost, int unlockLevel, int tier, int tierLock, int thresholdTicks) {
            PrestigePower power = new OriginalWardPrestigePower(thresholdTicks);
            assignSkillValues(power, id, name, pointCost, knowledgeCost, unlockLevel, 0, tier, tierLock);
            return this;
        }

        //TODO: add accessor for setting values on this power
        public Builder addPlayerStatPower(String id, String name, int pointCost, int knowledgeCost, int unlockLevel, int tier, int tierLock) {
            PrestigePower power = new PlayerStatPrestigePower();
            assignSkillValues(power, id, name, pointCost, knowledgeCost, unlockLevel, 0, tier, tierLock);
            return this;
        }

        public Builder addPotionEffectPower(String id, String name, int pointCost, int knowledgeCost, int unlockLevel, int tier, int tierLock, ResourceLocation effectId, int amplifier, boolean ambient, boolean visible) {
            PrestigePower power = new PotionEffectPrestigePower(unlockLevel, pointCost, 0, effectId, amplifier, ambient, visible);
            assignSkillValues(power, id, name, pointCost, knowledgeCost, unlockLevel, 0, tier, tierLock);
            return this;
        }

        //TODO
        public Builder addShieldedPower(String id, String name, int pointCost, int knowledgeCost, int unlockLevel, int tier, int tierLock, float percentageOfHearts) {
            PrestigePower power = new ShieldedPrestigePower();
            assignSkillValues(power, id, name, pointCost, knowledgeCost, unlockLevel, 0, tier, tierLock);
            return this;
        }

        public Builder addSuperCrystalsPower(String id, String name, int pointCost, int knowledgeCost, int unlockLevel, int tier, int tierLock, int extraCapacity) {
            PrestigePower power = new SuperCrystalsPrestigePower(extraCapacity);
            assignSkillValues(power, id, name, pointCost, knowledgeCost, unlockLevel, 0, tier, tierLock);
            return this;
        }

        public Builder addTemporalShardChancePower(String id, String name, int pointCost, int knowledgeCost, int unlockLevel, int tier, int tierLock, float chance) {
            PrestigePower power = new TemporalShardChancePrestigePower(chance);
            assignSkillValues(power, id, name, pointCost, knowledgeCost, unlockLevel, 0, tier, tierLock);
            return this;
        }

        public Builder addToolDurabilityPower(String id, String name, int pointCost, int knowledgeCost, int unlockLevel, int tier, int tierLock, float damageReduction) {
            PrestigePower power = new ToolDurabilityPrestigePower(damageReduction);
            assignSkillValues(power, id, name, pointCost, knowledgeCost, unlockLevel, 0, tier, tierLock);
            return this;
        }

        public Builder addTreasureHunterPower(String id, String name, int pointCost, int knowledgeCost, int unlockLevel, int tier, int tierLock, float bonusPerStack, int maxStacks) {
            PrestigePower power = new TreasureHunterPrestigePower(bonusPerStack, maxStacks);
            assignSkillValues(power, id, name, pointCost, knowledgeCost, unlockLevel, 0, tier, tierLock);
            return this;
        }

        public Builder addWingsPower(String id, String name, int pointCost, int knowledgeCost, int unlockLevel, int tier, int tierLock) {
            PrestigePower power = new WingsActivationPrestigePower();
            assignSkillValues(power, id, name, pointCost, knowledgeCost, unlockLevel, 0, tier, tierLock);
            return this;
        }

        public Builder addVanillaAttributePower(String id, String name, int pointCost, int knowledgeCost, int unlockLevel, int tier, int tierLock, Attribute attribute, AttributeModifier.Operation operation, double amoun) {
            PrestigePower power = new VanillaAttributePrestigePower(unlockLevel, pointCost, 0, attribute, operation, amoun);
            assignSkillValues(power, id, name, pointCost, knowledgeCost, unlockLevel, 0, tier, tierLock);
            return this;
        }

        public Builder add(String id, String name, int pointCost, int knowledgeCost, int unlockLevel, int tier, int tierLock, Supplier<PrestigePower> powerFactory) {
            PrestigePower power = powerFactory.get();
            assignSkillValues(power, id, name, pointCost, knowledgeCost, unlockLevel, 0, tier, tierLock);
            return this;
        }








        @Override
        protected void configureConfig(PrestigePowersConfig config) {
            config.tree = tree;
        }

    }
}
