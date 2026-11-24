package xyz.iwolfking.vhapi.api.loaders.misc;

import iskallia.vault.config.ChampionsConfig;
import iskallia.vault.init.ModConfigs;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.ChampionsConfigAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.ChampionsConfigRulesAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.ConfigAccessor;

public class ChampionsConfigLoader extends VaultConfigProcessor<ChampionsConfig> {
    public ChampionsConfigLoader() {
        super(new ChampionsConfig(), "champions");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        ChampionsConfigAccessor baseAccessor = (ChampionsConfigAccessor) ModConfigs.CHAMPIONS;
        boolean reindexNeeded = false;

        for (ChampionsConfig config : this.CUSTOM_CONFIGS.values()) {
            ChampionsConfigAccessor customAccessor = (ChampionsConfigAccessor) config;

            if (customAccessor.getMinimumVaultLevel() != null) {
                baseAccessor.setMinimumVaultLevel(customAccessor.getMinimumVaultLevel());
            }

            ModConfigs.CHAMPIONS.entityChampionChance.putAll(config.entityChampionChance);

            if (config.defaultChampionChance != 0.01F) {
                ModConfigs.CHAMPIONS.defaultChampionChance = config.defaultChampionChance;
            }

            if (config.entityAttributeOverrides != null) {
                ModConfigs.CHAMPIONS.entityAttributeOverrides.putAll(config.entityAttributeOverrides);
            }

            if (config.defaultAttributeOverrides != null) {
                Map<String, ChampionsConfig.AttributeOverride> attributeOverrideMap = new HashMap<>();
                List<ChampionsConfig.AttributeOverride> overridesToRemove = new ArrayList<>();

                for (ChampionsConfig.AttributeOverride override : config.defaultAttributeOverrides) {
                    attributeOverrideMap.put(override.NAME, override);
                }
                for (ChampionsConfig.AttributeOverride override : ModConfigs.CHAMPIONS.defaultAttributeOverrides) {
                    if (attributeOverrideMap.containsKey(override.NAME)) {
                        overridesToRemove.add(override);
                    }
                }
                ModConfigs.CHAMPIONS.defaultAttributeOverrides.removeAll(overridesToRemove);
                ModConfigs.CHAMPIONS.defaultAttributeOverrides.addAll(config.defaultAttributeOverrides);
            }

            if (customAccessor.getRules() != null) {
                ChampionsConfigRulesAccessor baseRules = (ChampionsConfigRulesAccessor) baseAccessor.getRules();
                ChampionsConfigRulesAccessor customRules = (ChampionsConfigRulesAccessor) customAccessor.getRules();

                if (baseRules != null) {
                    if (customRules.getCheckIntervalTicks() > 0) {
                        baseRules.setCheckIntervalTicks(customRules.getCheckIntervalTicks());
                    }
                    if (customRules.getHeightBlocks() > 0.0F) {
                        baseRules.setHeightBlocks(customRules.getHeightBlocks());
                    }
                    if (!customRules.getTargets().isEmpty()) {
                        for (String target : customRules.getTargets()) {
                            if (!baseRules.getTargets().contains(target)) {
                                baseRules.getTargets().add(target);
                            }
                        }
                    }
                    if (!customRules.getDifficulty().isEmpty()) {
                        baseRules.getDifficulty().putAll(customRules.getDifficulty());
                    }
                    reindexNeeded = true;
                }
            }

            if (customAccessor.getAuras() != null && !customAccessor.getAuras().isEmpty()) {
                if (baseAccessor.getAuras() != null) {
                    baseAccessor.getAuras().putAll(customAccessor.getAuras());
                } else {
                    baseAccessor.setAuras(customAccessor.getAuras());
                }
                reindexNeeded = true;
            }

            if (customAccessor.getOnHits() != null && !customAccessor.getOnHits().isEmpty()) {
                if (baseAccessor.getOnHits() != null) {
                    baseAccessor.getOnHits().putAll(customAccessor.getOnHits());
                } else {
                    baseAccessor.setOnHits(customAccessor.getOnHits());
                }
                reindexNeeded = true;
            }
        }

        if (reindexNeeded) {
            ((ConfigAccessor) ModConfigs.CHAMPIONS).vhapi$onLoad(null);
        }

        super.afterConfigsLoad(event);
    }
}