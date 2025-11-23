package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.entry.FloatRollRangeEntry;
import iskallia.vault.config.entry.IntRollRangeEntry;
import iskallia.vault.config.gear.VaultGearTierConfig;
import iskallia.vault.core.vault.influence.VaultGod;
import iskallia.vault.gear.attribute.VaultGearAttribute;
import iskallia.vault.gear.attribute.ability.*;
import iskallia.vault.gear.attribute.ability.special.base.SpecialAbilityConfig;
import iskallia.vault.gear.attribute.ability.special.base.SpecialAbilityConfigValue;
import iskallia.vault.gear.attribute.ability.special.base.SpecialAbilityGearAttribute;
import iskallia.vault.gear.attribute.ability.special.base.SpecialAbilityModification;
import iskallia.vault.gear.attribute.config.BooleanFlagGenerator;
import iskallia.vault.gear.attribute.config.DoubleAttributeGenerator;
import iskallia.vault.gear.attribute.config.FloatAttributeGenerator;
import iskallia.vault.gear.attribute.config.IntegerAttributeGenerator;
import iskallia.vault.gear.attribute.custom.RandomGodVaultModifierAttribute;
import iskallia.vault.gear.attribute.custom.RelentlessStrikeAttribute;
import iskallia.vault.gear.attribute.custom.ability.AbilityTriggerOnDamageAttribute;
import iskallia.vault.gear.attribute.custom.effect.*;
import iskallia.vault.gear.attribute.custom.loot.LootTriggerAttribute;
import iskallia.vault.gear.attribute.talent.RandomVaultModifierAttribute;
import iskallia.vault.gear.attribute.talent.TalentLevelAttribute;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import xyz.iwolfking.vhapi.api.lib.core.modifiers.StringValueGenerator;
import xyz.iwolfking.vhapi.mixin.accessors.ModifierGearTierAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.VaultGearTierConfigAccessor;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractVaultGearConfigProvider extends AbstractVaultConfigDataProvider {
    protected AbstractVaultGearConfigProvider(DataGenerator generator, String modid) {
        super(generator, modid, "gear/gear_modifiers");
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Gear Modifiers Data Provider";
    }

    public static class GearModifierConfigBuilder {
        private final Map<VaultGearTierConfig.ModifierAffixTagGroup, VaultGearTierConfig.AttributeGroup> modifierGroup = new LinkedHashMap<>();

        public GearModifierConfigBuilder add(VaultGearTierConfig.ModifierAffixTagGroup affixGroup, Consumer<VaultGearAttributeGroupBuilder> builderConsumer) {
            VaultGearAttributeGroupBuilder builder = new VaultGearAttributeGroupBuilder();
            builderConsumer.accept(builder);
            modifierGroup.put(affixGroup, builder.build());
            return this;
        }

        public VaultGearTierConfig build(ResourceLocation gearId) {
            VaultGearTierConfig newConfig = new VaultGearTierConfig(gearId);
            ((VaultGearTierConfigAccessor)newConfig).getModifierGroup().putAll(modifierGroup);
            return newConfig;
        }

    }

    public static class VaultGearAttributeGroupBuilder {
        public VaultGearTierConfig.AttributeGroup attributeGroup = new VaultGearTierConfig.AttributeGroup();

        public <T, V extends VaultGearTierConfig.ModifierTier<?>> VaultGearAttributeGroupBuilder addModifier(VaultGearAttribute<T> attribute, String modifierGroup, String identifier, List<String> tags, Consumer<VaultGearModifierTiersBuilder> tiersConsumer) {
            VaultGearTierConfig.ModifierTierGroup tierGroup = new VaultGearTierConfig.ModifierTierGroup(attribute, modifierGroup, identifier);
            VaultGearModifierTiersBuilder builder = new VaultGearModifierTiersBuilder();
            tiersConsumer.accept(builder);
            tierGroup.addAll(builder.build());
            tierGroup.getTags().addAll(tags);
            attributeGroup.add(tierGroup);
            return this;
        }

        public VaultGearTierConfig.AttributeGroup build() {
            return attributeGroup;
        }
    }

    public static class VaultGearModifierTiersBuilder {
        public List<VaultGearTierConfig.ModifierTier<?>> tiers = new ArrayList<>();

        private void configure(VaultGearTierConfig.ModifierTier<?> tier, int maxLevel) {
            ((ModifierGearTierAccessor)tier).setModifierTier(tiers.size());
            ((ModifierGearTierAccessor)tier).setMaxLevel(maxLevel);
        }

        public VaultGearModifierTiersBuilder add(VaultGearTierConfig.ModifierTier<?> tier, int maxLevel) {
            configure(tier, maxLevel);
            tiers.add(tier);
            return this;
        }

        public VaultGearModifierTiersBuilder add(int minLevel, int maxLevel, int weight, boolean flag) {
            return add(new VaultGearTierConfig.ModifierTier<>(minLevel, weight, new BooleanFlagGenerator.BooleanFlag(flag)), maxLevel);
        }

        public VaultGearModifierTiersBuilder add(int minLevel, int maxLevel, int weight, float min, float max, float step) {
            return add(new VaultGearTierConfig.ModifierTier<>(minLevel, weight, new FloatAttributeGenerator.Range(min, max, step)), maxLevel);
        }

        public VaultGearModifierTiersBuilder add(int minLevel, int maxLevel, int weight, double min, double max, double step) {
            return add(new VaultGearTierConfig.ModifierTier<>(minLevel, weight, new DoubleAttributeGenerator.Range(min, max, step)), maxLevel);
        }

        public VaultGearModifierTiersBuilder add(int minLevel, int maxLevel, int weight, int min, int max, int step) {
            return add(new VaultGearTierConfig.ModifierTier<>(minLevel, weight, new IntegerAttributeGenerator.Range(min, max, step)), maxLevel);
        }

        public VaultGearModifierTiersBuilder add(int minLevel, int maxLevel, int weight, AbilityLevelAttribute.Config config) {
            return add(new VaultGearTierConfig.ModifierTier<>(minLevel, weight, config), maxLevel);
        }

        public VaultGearModifierTiersBuilder add(int minLevel, int maxLevel, int weight, TalentLevelAttribute.Config config) {
            return add(new VaultGearTierConfig.ModifierTier<>(minLevel, weight, config), maxLevel);
        }

        public VaultGearModifierTiersBuilder add(int minLevel, int maxLevel, int weight, LootTriggerAttribute.Config config) {
            return add(new VaultGearTierConfig.ModifierTier<>(minLevel, weight, config), maxLevel);
        }

        public VaultGearModifierTiersBuilder add(int minLevel, int maxLevel, int weight, ResourceLocation effectId, int amplifier) {
            return add(new VaultGearTierConfig.ModifierTier<>(minLevel, weight, new EffectGearAttribute.Config(effectId, amplifier)), maxLevel);
        }

        public VaultGearModifierTiersBuilder add(int minLevel, int maxLevel, int weight, MobEffect effect, int amplifier) {
            return add(new VaultGearTierConfig.ModifierTier<>(minLevel, weight, new EffectGearAttribute.Config(effect, amplifier)), maxLevel);
        }

        public VaultGearModifierTiersBuilder add(int minLevel, int maxLevel, int weight, MobEffect effect, float minChance, float maxChance) {
            return add(new VaultGearTierConfig.ModifierTier<>(minLevel, weight, new EffectAvoidanceGearAttribute.Config(effect, minChance, maxChance)), maxLevel);
        }

        public VaultGearModifierTiersBuilder add(int minLevel, int maxLevel, int weight, ResourceLocation effectId, float minChance, float maxChance, float step) {
            return add(new VaultGearTierConfig.ModifierTier<>(minLevel, weight, new EffectAvoidanceGearAttribute.Config(effectId, minChance, maxChance, step)), maxLevel);
        }

        public VaultGearModifierTiersBuilder add(int minLevel, int maxLevel, int weight, List<ResourceLocation> effectIds, String name, float minChance, float maxChance, float step) {
            return add(new VaultGearTierConfig.ModifierTier<>(minLevel, weight, new EffectAvoidanceListGearAttribute.Config(effectIds, name, minChance, maxChance, step)), maxLevel);
        }

        public VaultGearModifierTiersBuilder add(int minLevel, int maxLevel, int weight, List<MobEffect> effects, String name, float minChance, float maxChance) {
            return add(new VaultGearTierConfig.ModifierTier<>(minLevel, weight, new EffectAvoidanceListGearAttribute.Config(effects, name, minChance, maxChance)), maxLevel);
        }

        public <T extends SpecialAbilityModification<C, V>, C extends SpecialAbilityConfig<V>, V extends SpecialAbilityConfigValue> VaultGearModifierTiersBuilder add(int minLevel, int maxLevel, int weight, String abilityKey, ResourceLocation specialModificationKey, C config) {
            return add(new VaultGearTierConfig.ModifierTier<>(minLevel, weight, new SpecialAbilityGearAttribute.SpecialAbilityTierConfig<T, C, V>(abilityKey, specialModificationKey, config)), maxLevel);
        }

        public VaultGearModifierTiersBuilder add(int minLevel, int maxLevel, int weight, String tooltipDisplayName, ResourceLocation effect, int duration, float radius, int color, boolean affectsOwner, float triggerChance, ResourceLocation additionalEffect, int additionalEffectDuration, int additionalEffectAmplifier) {
            EffectCloudAttribute.CloudConfig cloudConfig = new EffectCloudAttribute.CloudConfig(tooltipDisplayName, effect, duration, radius, color, affectsOwner, triggerChance);
            EffectCloudAttribute.CloudEffectConfig effectConfig = new EffectCloudAttribute.CloudEffectConfig(additionalEffect, additionalEffectDuration, additionalEffectAmplifier);
            cloudConfig.setAdditionalEffect(effectConfig);
            return add(new VaultGearTierConfig.ModifierTier<>(minLevel, weight, cloudConfig), maxLevel);
        }

        public VaultGearModifierTiersBuilder add(int minLevel, int maxLevel, int weight, ResourceLocation modifierId, int count, IntRollRangeEntry time) {
            return add(new VaultGearTierConfig.ModifierTier<>(minLevel, weight, new RandomVaultModifierAttribute.Config(modifierId, count, time)), maxLevel);
        }

        public VaultGearModifierTiersBuilder add(int minLevel, int maxLevel, int weight, ResourceLocation modifierId, int count, IntRollRangeEntry time, VaultGod god) {
            return add(new VaultGearTierConfig.ModifierTier<>(minLevel, weight, new RandomGodVaultModifierAttribute.Config(modifierId, count, time, god)), maxLevel);
        }

        public VaultGearModifierTiersBuilder add(int minLevel, int maxLevel, int weight, String value) {
            return add(new VaultGearTierConfig.ModifierTier<>(minLevel, weight, new StringValueGenerator.StringValue(value)), maxLevel);
        }

        public VaultGearModifierTiersBuilder add(int minLevel, int maxLevel, int weight, RelentlessStrikeAttribute.Config config) {
            return add(new VaultGearTierConfig.ModifierTier<>(minLevel, weight, config), maxLevel);
        }

        public VaultGearModifierTiersBuilder add(int minLevel, int maxLevel, int weight, String abilityId, FloatRollRangeEntry chance, IntRollRangeEntry level) {
            return add(new VaultGearTierConfig.ModifierTier<>(minLevel, weight, new AbilityTriggerOnDamageAttribute.Config(abilityId, chance, level)), maxLevel);
        }

        public VaultGearModifierTiersBuilder add(int minLevel, int maxLevel, int weight, ResourceLocation effectId, IntRollRangeEntry durationTicks) {
            return add(new VaultGearTierConfig.ModifierTier<>(minLevel, weight, new EffectTrialAttribute.Config(effectId, durationTicks)), maxLevel);
        }


        public VaultGearModifierTiersBuilder addFlatAbilityCooldown(int minLevel, int maxLevel, int weight, AbilityCooldownFlatAttribute.Config cooldownFlatConfig) {
            return add(new VaultGearTierConfig.ModifierTier<>(minLevel, weight, cooldownFlatConfig), maxLevel);
        }

        public VaultGearModifierTiersBuilder addPercentAbilityCooldown(int minLevel, int maxLevel, int weight, AbilityCooldownPercentAttribute.Config cooldownPercentConfig) {
            return add(new VaultGearTierConfig.ModifierTier<>(minLevel, weight, cooldownPercentConfig), maxLevel);
        }

        public VaultGearModifierTiersBuilder addFlatManaCost(int minLevel, int maxLevel, int weight, AbilityManaCostFlatAttribute.Config manaCostFlatConfig) {
            return add(new VaultGearTierConfig.ModifierTier<>(minLevel, weight, manaCostFlatConfig), maxLevel);
        }

        public VaultGearModifierTiersBuilder addPercentManaCost(int minLevel, int maxLevel, int weight, AbilityManaCostPercentAttribute.Config manaCostPercentConfig) {
            return add(new VaultGearTierConfig.ModifierTier<>(minLevel, weight, manaCostPercentConfig), maxLevel);
        }

        public VaultGearModifierTiersBuilder addAbilityAreaOfEffectFlat(int minLevel, int maxLevel, int weight, AbilityAreaOfEffectFlatAttribute.Config aoeFlatConfig) {
            return add(new VaultGearTierConfig.ModifierTier<>(minLevel, weight, aoeFlatConfig), maxLevel);
        }

        public VaultGearModifierTiersBuilder addAbilityAreaOfEffectPercent(int minLevel, int maxLevel, int weight, AbilityAreaOfEffectPercentAttribute.Config aoePercentConfig) {
            return add(new VaultGearTierConfig.ModifierTier<>(minLevel, weight, aoePercentConfig), maxLevel);
        }


        public List<VaultGearTierConfig.ModifierTier<?>> build() {
            return tiers;
        }
    }
}
