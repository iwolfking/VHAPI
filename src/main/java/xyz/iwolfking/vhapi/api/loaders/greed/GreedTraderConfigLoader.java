package xyz.iwolfking.vhapi.api.loaders.greed;

import iskallia.vault.config.greed.GreedTraderConfig;
import iskallia.vault.init.ModConfigs;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.GreedTraderConfigAccessor;

public class GreedTraderConfigLoader extends VaultConfigProcessor<GreedTraderConfig> {
    public GreedTraderConfigLoader() {
        super(new GreedTraderConfig(), "greed/greed_trader");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        GreedTraderConfig baseConfig = ModConfigs.GREED_TRADER;
        if (baseConfig == null) {
            super.afterConfigsLoad(event);
            return;
        }

        GreedTraderConfigAccessor baseAcc = (GreedTraderConfigAccessor) baseConfig;
        if (baseAcc.getWordsMutable() == null) {
            baseAcc.setWords(new LinkedHashMap<>());
        }
        if (baseAcc.getVoiceMutable() == null) {
            baseAcc.setVoice(new LinkedHashMap<>());
        }
        if (baseAcc.getPools() == null) {
            baseAcc.setPools(new LinkedHashMap<>());
        }
        if (baseAcc.getRoomPoolsByType() == null) {
            baseAcc.setRoomPoolsByType(new LinkedHashMap<>());
        }
        if (baseAcc.getModifierPools() == null) {
            baseAcc.setModifierPools(new LinkedHashMap<>());
        }

        for (GreedTraderConfig customConfig : this.CUSTOM_CONFIGS.values()) {
            GreedTraderConfigAccessor customAcc = (GreedTraderConfigAccessor) customConfig;

            if (customAcc.getEtchingPriceBase() != null) {
                baseAcc.setEtchingPriceBase(customAcc.getEtchingPriceBase());
            }
            if (customAcc.getEtchingPricePerTier() != null) {
                baseAcc.setEtchingPricePerTier(customAcc.getEtchingPricePerTier());
            }
            if (customAcc.getDripUnlockTier() != null) {
                baseAcc.setDripUnlockTier(customAcc.getDripUnlockTier());
            }
            if (customAcc.getHouse() != null) {
                baseAcc.setHouse(customAcc.getHouse());
            }

            if (customAcc.getWordsMutable() != null) {
                baseAcc.getWordsMutable().putAll(customAcc.getWordsMutable());
            }

            if (customAcc.getVoiceMutable() != null) {
                for (Map.Entry<String, List<String>> entry : customAcc.getVoiceMutable().entrySet()) {
                    if (entry.getKey() != null && entry.getValue() != null) {
                        List<String> targetLines = baseAcc.getVoiceMutable()
                                .computeIfAbsent(entry.getKey(), k -> new ArrayList<>());
                        targetLines.addAll(entry.getValue());
                    }
                }
            }

            if (customAcc.getPools() != null) {
                for (Map.Entry<String, List<GreedTraderConfig.PoolEntry>> entry : customAcc.getPools().entrySet()) {
                    if (entry.getKey() != null && entry.getValue() != null) {
                        List<GreedTraderConfig.PoolEntry> targetPool = baseAcc.getPools()
                                .computeIfAbsent(entry.getKey(), k -> new ArrayList<>());
                        targetPool.addAll(entry.getValue());
                    }
                }
            }

            if (customAcc.getRoomPoolsByType() != null) {
                for (Map.Entry<String, List<GreedTraderConfig.WeightedRoomPool>> entry : customAcc.getRoomPoolsByType().entrySet()) {
                    if (entry.getKey() != null && entry.getValue() != null) {
                        String normalizedType = entry.getKey().toUpperCase(Locale.ROOT);
                        List<GreedTraderConfig.WeightedRoomPool> targetRooms = baseAcc.getRoomPoolsByType()
                                .computeIfAbsent(normalizedType, k -> new ArrayList<>());
                        targetRooms.addAll(entry.getValue());
                    }
                }
            }

            if (customAcc.getModifierPools() != null) {
                for (Map.Entry<String, List<GreedTraderConfig.WeightedModifier>> entry : customAcc.getModifierPools().entrySet()) {
                    if (entry.getKey() != null && entry.getValue() != null) {
                        List<GreedTraderConfig.WeightedModifier> targetModifiers = baseAcc.getModifierPools()
                                .computeIfAbsent(entry.getKey(), k -> new ArrayList<>());
                        targetModifiers.addAll(entry.getValue());
                    }
                }
            }
        }

        super.afterConfigsLoad(event);
    }
}