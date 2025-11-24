package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.TalentsConfig;
import iskallia.vault.core.world.data.entity.EntityPredicate;
import iskallia.vault.gear.VaultGearRarity;
import iskallia.vault.gear.attribute.VaultGearAttribute;
import iskallia.vault.skill.base.LearnableSkill;
import iskallia.vault.skill.base.TieredSkill;
import iskallia.vault.skill.expertise.type.FarmerTwerker;
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
import iskallia.vault.skill.tree.TalentTree;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.FarmerTwerkerAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.PuristTalentAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.TieredSkillAccessor;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.IntToDoubleFunction;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public abstract class AbstractTalentProvider extends AbstractVaultConfigDataProvider<AbstractTalentProvider.Builder> {
    protected AbstractTalentProvider(DataGenerator generator, String modid) {
        super(generator, modid, "talents/talents", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Talents Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<TalentsConfig> {
        TalentTree tree = new TalentTree();

        public Builder() {
            super(TalentsConfig::new);
        }

        private void assignSkillValues(TieredSkill skill, String id, String name, int maxLearnableTier) {
            skill.setId(id);
            skill.setName(name);
            ((TieredSkillAccessor)skill).setMaxLearnableTier(maxLearnableTier);
            tree.skills.add(skill);
        }


        public Builder addPuristTalent(String id, String name, int maxLearnableTier, int unlockLevel, int learnPointCost, int numberOfTiers, IntToDoubleFunction scalingFunction, Consumer<Set<VaultGearRarity>> raritiesConsumer, Consumer<Set<EquipmentSlot>> slotsConsumer) {
            Set<VaultGearRarity> rarities = new HashSet<>();
            Set<EquipmentSlot> slots = new HashSet<>();
            raritiesConsumer.accept(rarities);
            slotsConsumer.accept(slots);

            TieredSkill skill = new TieredSkill(unlockLevel, learnPointCost, 1, IntStream.range(0, numberOfTiers).mapToObj(i -> {
                PuristTalent talent = new PuristTalent();
                ((PuristTalentAccessor)talent).setDamageIncrease((float) scalingFunction.applyAsDouble(i));
                ((PuristTalentAccessor)talent).setRarities(rarities);
                ((PuristTalentAccessor)talent).setSlots(slots);
                return talent;
            }));

            assignSkillValues(skill, id, name, maxLearnableTier);

            return this;
        }

        public Builder addVanillaAttributeTalent(String id, String name, int maxLearnableTier, int unlockLevel, int learnPointCost, int numberOfTiers, Attribute attribute, AttributeModifier.Operation operation, IntToDoubleFunction scalingFunction) {
            TieredSkill skill = new TieredSkill(unlockLevel, learnPointCost, 1, IntStream.range(0, numberOfTiers).mapToObj(i -> {
                return new VanillaAttributeTalent(unlockLevel, learnPointCost, 1, attribute, operation, scalingFunction.applyAsDouble(i));
            }));

            assignSkillValues(skill, id, name, maxLearnableTier);

            return this;
        }

        public Builder addEffectTalent(String id, String name, int maxLearnableTier, int unlockLevel, int learnPointCost, int numberOfTiers, MobEffect effect, IntFunction<Integer> amplitudeScalingFunction) {
            TieredSkill skill = new TieredSkill(unlockLevel, learnPointCost, 1, IntStream.range(0, numberOfTiers).mapToObj(i -> {
                return new EffectTalent(unlockLevel, learnPointCost, 1, effect, amplitudeScalingFunction.apply(i));
            }));

            assignSkillValues(skill, id, name, maxLearnableTier);

            return this;
        }

        public Builder addGearAttributeTalent(String id, String name, int maxLearnableTier, int unlockLevel, int learnPointCost, int numberOfTiers, VaultGearAttribute<?> attribute, IntToDoubleFunction scalingFunction) {
            TieredSkill skill = new TieredSkill(unlockLevel, learnPointCost, 1, IntStream.range(0, numberOfTiers).mapToObj(i -> {
                return new GearAttributeTalent(unlockLevel, learnPointCost, 1, attribute, scalingFunction.applyAsDouble(i));
            }));

            assignSkillValues(skill, id, name, maxLearnableTier);

            return this;
        }

        public Builder addStackingGearAttributeTalent(String id, String name, int maxLearnableTier, int unlockLevel, int learnPointCost, int numberOfTiers, VaultGearAttribute<?> attribute, IntToDoubleFunction scalingFunction, MobEffect effect, IntFunction<Integer> stacksScaling, IntFunction<Integer> durationScaling) {
            TieredSkill skill = new TieredSkill(unlockLevel, learnPointCost, 1, IntStream.range(0, numberOfTiers).mapToObj(i -> {
                return new StackingGearAttributeTalent(unlockLevel, learnPointCost, 1, attribute, scalingFunction.applyAsDouble(i), effect, durationScaling.apply(i), stacksScaling.apply(i));
            }));

            assignSkillValues(skill, id, name, maxLearnableTier);

            return this;
        }

        public Builder addHighHealthGearAttributeTalent(String id, String name, int maxLearnableTier, int unlockLevel, int learnPointCost, int numberOfTiers, MobEffect effect, IntToDoubleFunction healthThresholdScaling, VaultGearAttribute<?> attribute, IntToDoubleFunction valueScalingFunction) {
            TieredSkill skill = new TieredSkill(unlockLevel, learnPointCost, 1, IntStream.range(0, numberOfTiers).mapToObj(i -> {
                return new HighHealthGearAttributeTalent(unlockLevel, learnPointCost, 1, effect, (float)healthThresholdScaling.applyAsDouble(i), attribute, valueScalingFunction.applyAsDouble(i), null);
            }));

            assignSkillValues(skill, id, name, maxLearnableTier);

            return this;
        }

        public Builder addHighManaGearAttributeTalent(String id, String name, int maxLearnableTier, int unlockLevel, int learnPointCost, int numberOfTiers, MobEffect effect, IntToDoubleFunction manaThresholdScaling, VaultGearAttribute<?> attribute, IntToDoubleFunction valueScalingFunction) {
            TieredSkill skill = new TieredSkill(unlockLevel, learnPointCost, 1, IntStream.range(0, numberOfTiers).mapToObj(i -> {
                return new HighManaGearAttributeTalent(unlockLevel, learnPointCost, 1, effect, (float)manaThresholdScaling.applyAsDouble(i), attribute, valueScalingFunction.applyAsDouble(i), null);
            }));

            assignSkillValues(skill, id, name, maxLearnableTier);

            return this;
        }

        public Builder addLowManaDamageTalent(String id, String name, int maxLearnableTier, int unlockLevel, int learnPointCost, int numberOfTiers, MobEffect effect, IntToDoubleFunction manaThresholdScaling, IntToDoubleFunction valueScalingFunction) {
            TieredSkill skill = new TieredSkill(unlockLevel, learnPointCost, 1, IntStream.range(0, numberOfTiers).mapToObj(i -> {
                return new LowManaDamageTalent(unlockLevel, learnPointCost, 1, effect, (float)manaThresholdScaling.applyAsDouble(i), (float)valueScalingFunction.applyAsDouble(i));
            }));

            assignSkillValues(skill, id, name, maxLearnableTier);

            return this;
        }

        public Builder addLightningDamageTalent(String id, String name, int maxLearnableTier, int unlockLevel, int learnPointCost, int numberOfTiers, IntToDoubleFunction scalingFunction) {
            TieredSkill skill = new TieredSkill(unlockLevel, learnPointCost, 1, IntStream.range(0, numberOfTiers).mapToObj(i -> {
                return new LightningDamageTalent(unlockLevel, learnPointCost, 1, (float)scalingFunction.applyAsDouble(i));
            }));

            assignSkillValues(skill, id, name, maxLearnableTier);

            return this;
        }

        public Builder addJavelinThrowPowerTalent(String id, String name, int maxLearnableTier, int unlockLevel, int learnPointCost, int numberOfTiers, IntToDoubleFunction scalingFunction) {
            TieredSkill skill = new TieredSkill(unlockLevel, learnPointCost, 1, IntStream.range(0, numberOfTiers).mapToObj(i -> {
                return new JavelinThrowPowerTalent(unlockLevel, learnPointCost, 1, (float)scalingFunction.applyAsDouble(i));
            }));

            assignSkillValues(skill, id, name, maxLearnableTier);

            return this;
        }

        public Builder addJavelinFrugalTalent(String id, String name, int maxLearnableTier, int unlockLevel, int learnPointCost, int numberOfTiers, IntToDoubleFunction scalingFunction) {
            TieredSkill skill = new TieredSkill(unlockLevel, learnPointCost, 1, IntStream.range(0, numberOfTiers).mapToObj(i -> {
                return new JavelinFrugalTalent(unlockLevel, learnPointCost, 1, (float)scalingFunction.applyAsDouble(i));
            }));

            assignSkillValues(skill, id, name, maxLearnableTier);

            return this;
        }


        public Builder addJavelinDamageTalent(String id, String name, int maxLearnableTier, int unlockLevel, int learnPointCost, int numberOfTiers, IntToDoubleFunction scalingFunction) {
            TieredSkill skill = new TieredSkill(unlockLevel, learnPointCost, 1, IntStream.range(0, numberOfTiers).mapToObj(i -> {
                return new JavelinDamageTalent(unlockLevel, learnPointCost, 1, (float)scalingFunction.applyAsDouble(i));
            }));

            assignSkillValues(skill, id, name, maxLearnableTier);

            return this;
        }


        public Builder addJavelinConductTalent(String id, String name, int maxLearnableTier, int unlockLevel, int learnPointCost, int numberOfTiers) {
            TieredSkill skill = new TieredSkill(unlockLevel, learnPointCost, 1, IntStream.range(0, numberOfTiers).mapToObj(i -> {
                return new JavelinConductTalent(unlockLevel, learnPointCost, 1);
            }));

            assignSkillValues(skill, id, name, maxLearnableTier);

            return this;
        }

        public Builder addLuckyHitDamageTalent(String id, String name, int maxLearnableTier, int unlockLevel, int learnPointCost, int numberOfTiers, IntToDoubleFunction scalingFunction) {
            TieredSkill skill = new TieredSkill(unlockLevel, learnPointCost, 1, IntStream.range(0, numberOfTiers).mapToObj(i -> {
                return new DamageLuckyHitTalent(unlockLevel, learnPointCost, 1, (float)scalingFunction.applyAsDouble(i));
            }));

            assignSkillValues(skill, id, name, maxLearnableTier);

            return this;
        }

        public Builder addLuckyHitManaTalent(String id, String name, int maxLearnableTier, int unlockLevel, int learnPointCost, int numberOfTiers, IntToDoubleFunction scalingFunction) {
            TieredSkill skill = new TieredSkill(unlockLevel, learnPointCost, 1, IntStream.range(0, numberOfTiers).mapToObj(i -> {
                return new ManaLeechLuckyHitTalent(unlockLevel, learnPointCost, 1, (float)scalingFunction.applyAsDouble(i));
            }));

            assignSkillValues(skill, id, name, maxLearnableTier);

            return this;
        }

        public Builder addLuckyHitSweepingTalent(String id, String name, int maxLearnableTier, int unlockLevel, int learnPointCost, int numberOfTiers, IntToDoubleFunction damagePercentageScaling, IntToDoubleFunction damageRangeScaling, IntToDoubleFunction knockbackStrengthScaling) {
            TieredSkill skill = new TieredSkill(unlockLevel, learnPointCost, 1, IntStream.range(0, numberOfTiers).mapToObj(i -> {
                return new SweepingLuckyHitTalent(unlockLevel, learnPointCost, 1, (float)damagePercentageScaling.applyAsDouble(i), (float)damageRangeScaling.applyAsDouble(i), (float)knockbackStrengthScaling.applyAsDouble(i));
            }));

            assignSkillValues(skill, id, name, maxLearnableTier);

            return this;
        }

        public Builder addFarmerTwerkerTalent(String id, String name, int maxLearnableTier, int unlockLevel, int learnPointCost, int numberOfTiers, IntToDoubleFunction adultChanceScaling, IntFunction<Integer> delayScaling, IntFunction<Integer> horizontalScaling, IntFunction<Integer> verticalScaling) {
            TieredSkill skill = new TieredSkill(unlockLevel, learnPointCost, 1, IntStream.range(0, numberOfTiers).mapToObj(i -> {
                FarmerTwerker talent = new FarmerTwerker(unlockLevel, learnPointCost, 1);
                ((FarmerTwerkerAccessor)talent).setAdultChance((float) adultChanceScaling.applyAsDouble(i));
                ((FarmerTwerkerAccessor)talent).setTickDelay(delayScaling.apply(i));
                ((FarmerTwerkerAccessor)talent).setHorizontalRange(horizontalScaling.apply(i));
                ((FarmerTwerkerAccessor)talent).setVerticalRange(verticalScaling.apply(i));
                return talent;
            }));

            assignSkillValues(skill, id, name, maxLearnableTier);

            return this;
        }

        public Builder addCastOnHitTalent(String id, String name, int maxLearnableTier, int unlockLevel, int learnPointCost, int numberOfTiers, Consumer<EntityPredicate[]> filterConsumer, String ability, IntToDoubleFunction probabilityScaling) {
            TieredSkill skill = new TieredSkill(unlockLevel, learnPointCost, 1, IntStream.range(0, numberOfTiers).mapToObj(i -> {
                EntityPredicate[] filter = new EntityPredicate[]{};
                filterConsumer.accept(filter);
                return new CastOnHitTalent(unlockLevel, learnPointCost, 1, filter, ability, (float)probabilityScaling.applyAsDouble(i));
            }));

            assignSkillValues(skill, id, name, maxLearnableTier);

            return this;
        }

        public Builder addEffectOnHitTalent(String id, String name, int maxLearnableTier, int unlockLevel, int learnPointCost, int numberOfTiers, Consumer<EntityPredicate[]> filterConsumer, MobEffect effect, IntToDoubleFunction probabilityScaling, IntFunction<Integer> amplifierScaling, IntFunction<Integer> durationScaling) {
            TieredSkill skill = new TieredSkill(unlockLevel, learnPointCost, 1, IntStream.range(0, numberOfTiers).mapToObj(i -> {
                EntityPredicate[] filter = new EntityPredicate[]{};
                filterConsumer.accept(filter);
                return new EffectOnHitTalent(unlockLevel, learnPointCost, 1, filter, effect, amplifierScaling.apply(i), durationScaling.apply(i), (float)probabilityScaling.applyAsDouble(i));
            }));

            assignSkillValues(skill, id, name, maxLearnableTier);

            return this;
        }

        public Builder addDamageOnHitTalent(String id, String name, int maxLearnableTier, int unlockLevel, int learnPointCost, int numberOfTiers, Consumer<EntityPredicate[]> filterConsumer, IntToDoubleFunction probabilityScaling) {
            TieredSkill skill = new TieredSkill(unlockLevel, learnPointCost, 1, IntStream.range(0, numberOfTiers).mapToObj(i -> {
                EntityPredicate[] filter = new EntityPredicate[]{};
                filterConsumer.accept(filter);
                return new DamageOnHitTalent(unlockLevel, learnPointCost, 1, filter, (float)probabilityScaling.applyAsDouble(i));
            }));

            assignSkillValues(skill, id, name, maxLearnableTier);

            return this;
        }

        public Builder addCastOnKillTalent(String id, String name, int maxLearnableTier, int unlockLevel, int learnPointCost, int numberOfTiers, Consumer<EntityPredicate[]> filterConsumer, String ability, IntToDoubleFunction probabilityScaling) {
            TieredSkill skill = new TieredSkill(unlockLevel, learnPointCost, 1, IntStream.range(0, numberOfTiers).mapToObj(i -> {
                EntityPredicate[] filter = new EntityPredicate[]{};
                filterConsumer.accept(filter);
                return new CastOnKillTalent(unlockLevel, learnPointCost, 1, filter, ability, (float)probabilityScaling.applyAsDouble(i));
            }));

            assignSkillValues(skill, id, name, maxLearnableTier);

            return this;
        }

        public Builder addPrudentTalent(String id, String name, int maxLearnableTier, int unlockLevel, int learnPointCost, int numberOfTiers, IntToDoubleFunction scalingFunction) {
            TieredSkill skill = new TieredSkill(unlockLevel, learnPointCost, 1, IntStream.range(0, numberOfTiers).mapToObj(i -> {
                return new PrudentTalent(unlockLevel, learnPointCost, 1, (float)scalingFunction.applyAsDouble(i));
            }));

            assignSkillValues(skill, id, name, maxLearnableTier);

            return this;
        }

        public Builder addAlchemistTalent(String id, String name, int maxLearnableTier, int unlockLevel, int learnPointCost, int numberOfTiers, IntToDoubleFunction scalingFunction) {
            TieredSkill skill = new TieredSkill(unlockLevel, learnPointCost, 1, IntStream.range(0, numberOfTiers).mapToObj(i -> {
                return new AlchemistTalent(unlockLevel, learnPointCost, 1, (float)scalingFunction.applyAsDouble(i));
            }));

            assignSkillValues(skill, id, name, maxLearnableTier);

            return this;
        }

        public <T extends LearnableSkill> Builder addTalent(String id, String name, int maxLearnableTier, int unlockLevel, int learnPointCost, int numberOfTiers, IntFunction<T> talentFactory) {
            TieredSkill skill = new TieredSkill(unlockLevel, learnPointCost, 1, IntStream.range(0, numberOfTiers).mapToObj(talentFactory));

            assignSkillValues(skill, id, name, maxLearnableTier);

            return this;
        }


        @Override
        protected void configureConfig(TalentsConfig config) {
            config.tree = tree;
        }

    }
}
