package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.ChampionsConfig;
import iskallia.vault.core.world.data.entity.EntityPredicate;
import iskallia.vault.entity.champion.ChampionAura;
import iskallia.vault.entity.champion.ChampionOnHit;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.data.DataGenerator;
import xyz.iwolfking.vhapi.api.datagen.lib.BasicListBuilder;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.ChampionsConfigAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.ChampionsConfigAuraSettingsAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.ChampionsConfigDifficultyRuleAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.ChampionsConfigOnHitSettingsAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.ChampionsConfigRulesAccessor;

public abstract class AbstractChampionProvider extends AbstractVaultConfigDataProvider<AbstractChampionProvider.Builder> {
    protected AbstractChampionProvider(DataGenerator generator, String modid) {
        super(generator, modid, "champions", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Champions Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<ChampionsConfig> {
        private float defaultChampionChance = 0.01F;
        @Nullable
        private Integer minimumVaultLevel;
        private final LinkedHashMap<EntityPredicate, Float> entityChampionChance = new LinkedHashMap<>();
        private final List<ChampionsConfig.AttributeOverride> defaultAttributeOverrides = new ArrayList<>();
        private final LinkedHashMap<EntityPredicate, List<ChampionsConfig.AttributeOverride>> entityAttributeOverrides = new LinkedHashMap<>();
        @Nullable
        private Integer checkIntervalTicks;
        @Nullable
        private Float heightBlocks;
        private final List<String> targetGroups = new ArrayList<>();
        private final Map<String, ChampionsConfig.DifficultyRule> difficultyRules = new LinkedHashMap<>();
        private final Map<String, ChampionsConfig.AuraSettings> auraSettings = new LinkedHashMap<>();
        private final Map<String, ChampionsConfig.OnHitSettings> onHitSettings = new LinkedHashMap<>();

        public Builder() {
            super(ChampionsConfig::new);
        }

        public Builder setChampionChance(float championChance) {
            this.defaultChampionChance = championChance;
            return this;
        }

        public Builder setMinimumVaultLevel(int minimumVaultLevel) {
            this.minimumVaultLevel = minimumVaultLevel;
            return this;
        }

        public Builder addChampionChance(EntityPredicate predicate, float chance) {
            entityChampionChance.put(predicate, chance);
            return this;
        }

        public Builder addDefaultAttributeOverride(String name, double value, String operator) {
            defaultAttributeOverrides.add(new ChampionsConfig.AttributeOverride(name, value, operator));
            return this;
        }

        public Builder addEntityAttributeOverride(EntityPredicate predicate, Consumer<BasicListBuilder<ChampionsConfig.AttributeOverride>> builderConsumer) {
            BasicListBuilder<ChampionsConfig.AttributeOverride> builder = new BasicListBuilder<>();
            builderConsumer.accept(builder);
            entityAttributeOverrides.put(predicate, builder.build());
            return this;
        }

        public Builder configureRules(Consumer<ChampionsConfigRulesAccessor> rulesConsumer) {
            ChampionsConfig.Rules rules = new ChampionsConfig.Rules();
            rulesConsumer.accept((ChampionsConfigRulesAccessor) rules);
            if (this.checkIntervalTicks != null) {
                ((ChampionsConfigRulesAccessor) rules).setCheckIntervalTicks(this.checkIntervalTicks);
            }
            if (this.heightBlocks != null) {
                ((ChampionsConfigRulesAccessor) rules).setHeightBlocks(this.heightBlocks);
            }
            if (!this.targetGroups.isEmpty()) {
                ((ChampionsConfigRulesAccessor) rules).setTargets(new ArrayList<>(this.targetGroups));
            }
            if (!this.difficultyRules.isEmpty()) {
                ((ChampionsConfigRulesAccessor) rules).setDifficulty(new LinkedHashMap<>(this.difficultyRules));
            }
            this.checkIntervalTicks = ((ChampionsConfigRulesAccessor) rules).getCheckIntervalTicks();
            this.heightBlocks = ((ChampionsConfigRulesAccessor) rules).getHeightBlocks();
            this.targetGroups.clear();
            this.targetGroups.addAll(((ChampionsConfigRulesAccessor) rules).getTargets());
            this.difficultyRules.clear();
            this.difficultyRules.putAll(((ChampionsConfigRulesAccessor) rules).getDifficulty());
            return this;
        }

        public Builder setDifficultyRule(String difficultyKey, boolean aura, boolean onHit) {
            ChampionsConfig.DifficultyRule rule = new ChampionsConfig.DifficultyRule(aura, onHit);
            ((ChampionsConfigDifficultyRuleAccessor) rule).setAura(aura);
            ((ChampionsConfigDifficultyRuleAccessor) rule).setOnHit(onHit);
            this.difficultyRules.put(difficultyKey, rule);
            return this;
        }

        public Builder putAuraSettings(ChampionAura aura, String color, float radius, float value, float weight, @Nullable Consumer<ChampionsConfigAuraSettingsAccessor> customizer) {
            ChampionsConfig.AuraSettings settings = new ChampionsConfig.AuraSettings(color, radius, value, weight);
            if (customizer != null) {
                customizer.accept((ChampionsConfigAuraSettingsAccessor) settings);
            }
            this.auraSettings.put(aura.getId(), settings);
            return this;
        }

        public Builder putOnHitSettings(ChampionOnHit onHit, float value, int maxStacks, int durationTicks, @Nullable Consumer<ChampionsConfigOnHitSettingsAccessor> customizer) {
            ChampionsConfig.OnHitSettings settings = new ChampionsConfig.OnHitSettings(value, maxStacks, durationTicks);
            if (customizer != null) {
                customizer.accept((ChampionsConfigOnHitSettingsAccessor) settings);
            }
            this.onHitSettings.put(onHit.getId(), settings);
            return this;
        }

        @Override
        protected void configureConfig(ChampionsConfig config) {
            ChampionsConfigAccessor accessor = (ChampionsConfigAccessor) config;
            config.defaultChampionChance = defaultChampionChance;
            if (minimumVaultLevel != null) {
                accessor.setMinimumVaultLevel(minimumVaultLevel);
            }
            config.defaultAttributeOverrides.addAll(defaultAttributeOverrides);
            config.entityChampionChance.putAll(entityChampionChance);
            config.entityAttributeOverrides.putAll(entityAttributeOverrides);

            if (checkIntervalTicks != null || heightBlocks != null || !targetGroups.isEmpty() || !difficultyRules.isEmpty()) {
                ChampionsConfig.Rules rules = new ChampionsConfig.Rules();
                ChampionsConfigRulesAccessor rulesAccessor = (ChampionsConfigRulesAccessor) rules;
                if (checkIntervalTicks != null) {
                    rulesAccessor.setCheckIntervalTicks(checkIntervalTicks);
                }
                if (heightBlocks != null) {
                    rulesAccessor.setHeightBlocks(heightBlocks);
                }
                if (!targetGroups.isEmpty()) {
                    rulesAccessor.setTargets(new ArrayList<>(targetGroups));
                }
                if (!difficultyRules.isEmpty()) {
                    rulesAccessor.setDifficulty(new LinkedHashMap<>(difficultyRules));
                }
                accessor.setRules(rules);
            }

            if (!auraSettings.isEmpty()) {
                accessor.setAuras(new LinkedHashMap<>(auraSettings));
            }

            if (!onHitSettings.isEmpty()) {
                accessor.setOnHits(new LinkedHashMap<>(onHitSettings));
            }
        }
    }
}