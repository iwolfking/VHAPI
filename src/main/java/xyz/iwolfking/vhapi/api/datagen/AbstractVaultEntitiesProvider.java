package xyz.iwolfking.vhapi.api.datagen;

import com.google.gson.annotations.Expose;
import iskallia.vault.config.TooltipConfig;
import iskallia.vault.config.VaultEntitiesConfig;
import iskallia.vault.core.world.data.entity.EntityPredicate;
import iskallia.vault.gear.attribute.custom.effect.EffectCloudAttribute;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.TooltipConfigAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.VaultEntitiesConfigAccessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractVaultEntitiesProvider extends AbstractVaultConfigDataProvider<AbstractVaultEntitiesProvider.Builder> {
    protected AbstractVaultEntitiesProvider(DataGenerator generator, String modid) {
        super(generator, modid, "vault/entities", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Tooltip Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<VaultEntitiesConfig> {
        private final List<VaultEntitiesConfig.DeathEffect> deathEffects = new ArrayList<>();
        private final List<VaultEntitiesConfig.ThrowEffect> throwEffects = new ArrayList<>();

        public Builder() {
            super(VaultEntitiesConfig::new);
        }

        public Builder addDeathEffect(EntityPredicate filter, Consumer<DeathEffectBuilder> builderConsumer) {
            DeathEffectBuilder builder = new DeathEffectBuilder();
            builderConsumer.accept(builder);
            VaultEntitiesConfig.DeathEffect deathEffect = new VaultEntitiesConfig.DeathEffect(filter);
            builder.build().forEach(deathEffect::put);
            deathEffects.add(deathEffect);
            return this;
        }

        public Builder addThrowEffect(EntityPredicate filter, Consumer<CustomEffectBuilder> builderConsumer) {
            CustomEffectBuilder builder = new CustomEffectBuilder();
            VaultEntitiesConfig.ThrowEffect effect = new VaultEntitiesConfig.ThrowEffect(filter);
            builderConsumer.accept(builder);
            builder.build().forEach(effect::put);
            throwEffects.add(effect);
            return this;
        }

        @Override
        protected void configureConfig(VaultEntitiesConfig config) {
            ((VaultEntitiesConfigAccessor)config).setDeathEffects(deathEffects);
            ((VaultEntitiesConfigAccessor)config).setThrowEffects(throwEffects);
        }

        public static class DeathEffectBuilder {
            private final Map<Integer, EffectCloudAttribute.CloudConfig> deathEffectMap = new HashMap<>();

            public DeathEffectBuilder add(int level, String tooltipDisplayName, ResourceLocation potion, int duration, float radius, int color, boolean affectsOwner, float triggerChance, ResourceLocation additionalEffect, int additionalDuration, int amplifier ) {
                EffectCloudAttribute.CloudConfig config1 = new EffectCloudAttribute.CloudConfig(tooltipDisplayName, potion, duration, radius, color, affectsOwner, triggerChance);
                config1.setAdditionalEffect(new EffectCloudAttribute.CloudEffectConfig(additionalEffect, additionalDuration, amplifier));
                deathEffectMap.put(level, config1);
                return this;
            }

            public Map<Integer, EffectCloudAttribute.CloudConfig> build() {
                return deathEffectMap;
            }
        }

        public static class CustomEffectBuilder {
            private final Map<Integer, VaultEntitiesConfig.ThrowEffect.CustomEffect> effectMap = new HashMap<>();

            public CustomEffectBuilder add(int level, ResourceLocation effect, int duration, int amplfier) {
                effectMap.put(level, new VaultEntitiesConfig.ThrowEffect.CustomEffect(effect, duration, amplfier));
                return this;
            }

            public CustomEffectBuilder add(int level, MobEffect effect, int duration, int amplfier) {
                add(level, effect.getRegistryName(), duration, amplfier);
                return this;
            }

            public Map<Integer, VaultEntitiesConfig.ThrowEffect.CustomEffect> build() {
                return effectMap;
            }
        }
    }
}
