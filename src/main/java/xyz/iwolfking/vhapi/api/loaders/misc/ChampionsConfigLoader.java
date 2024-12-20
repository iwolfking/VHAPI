package xyz.iwolfking.vhapi.api.loaders.misc;

import iskallia.vault.config.ChampionsConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChampionsConfigLoader extends VaultConfigProcessor<ChampionsConfig> {
    public ChampionsConfigLoader() {
        super(new ChampionsConfig(), "champions");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ChampionsConfig config : this.CUSTOM_CONFIGS.values()) {


            ModConfigs.CHAMPIONS.entityChampionChance.putAll(config.entityChampionChance);

            if(config.defaultChampionChance != 0.01F) {
                ModConfigs.CHAMPIONS.defaultChampionChance = config.defaultChampionChance;
            }

            if(config.entityAffixesData != null) {
                ModConfigs.CHAMPIONS.entityAffixesData.putAll(config.entityAffixesData);
            }

            if(config.entityAttributeOverrides != null) {
                ModConfigs.CHAMPIONS.entityAttributeOverrides.putAll(config.entityAttributeOverrides);
            }

            if(config.defaultAttributeOverrides != null) {
                Map<String, ChampionsConfig.AttributeOverride> attributeOverrideMap = new HashMap<>();
                List<ChampionsConfig.AttributeOverride> overridesToRemove = new ArrayList<>();

                for(ChampionsConfig.AttributeOverride override : config.defaultAttributeOverrides) {
                    attributeOverrideMap.put(override.NAME, override);
                }
                for(ChampionsConfig.AttributeOverride override : ModConfigs.CHAMPIONS.defaultAttributeOverrides) {
                    if(attributeOverrideMap.containsKey(override.NAME)) {
                        overridesToRemove.add(override);
                    }
                }
                ModConfigs.CHAMPIONS.defaultAttributeOverrides.removeAll(overridesToRemove);
                ModConfigs.CHAMPIONS.defaultAttributeOverrides.addAll(config.defaultAttributeOverrides);
            }
        }
        super.afterConfigsLoad(event);
    }
}
