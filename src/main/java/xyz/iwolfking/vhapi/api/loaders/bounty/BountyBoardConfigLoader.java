package xyz.iwolfking.vhapi.api.loaders.bounty;

import iskallia.vault.config.bounty.BountyBoardConfig;
import iskallia.vault.config.entry.IntRangeEntry;
import iskallia.vault.gear.VaultGearRarity;
import iskallia.vault.init.ModConfigs;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.BountyBoardConfigAccessor;

public class BountyBoardConfigLoader extends VaultConfigProcessor<BountyBoardConfig> {
    public BountyBoardConfigLoader() {
        super(new BountyBoardConfig(), "bounty/board");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        BountyBoardConfigAccessor base = (BountyBoardConfigAccessor) ModConfigs.BOUNTY_BOARD;

        for (BountyBoardConfig config : this.CUSTOM_CONFIGS.values()) {
            BountyBoardConfigAccessor custom = (BountyBoardConfigAccessor) config;

            if (custom.getOfferedScrolls() > 0) {
                base.setOfferedScrolls(custom.getOfferedScrolls());
            }
            if (custom.getBaseActiveBounties() > 0) {
                base.setBaseActiveBounties(custom.getBaseActiveBounties());
            }
            if (custom.getRefreshBasePrice() > 0.0F) {
                base.setRefreshBasePrice(custom.getRefreshBasePrice());
            }
            if (custom.getRefreshPriceGrowth() > 0.0F) {
                base.setRefreshPriceGrowth(custom.getRefreshPriceGrowth());
            }
            base.setHeatPerRefresh(custom.getHeatPerRefresh());
            base.setHeatPerAbandon(custom.getHeatPerAbandon());
            base.setCoolingPerFinishedBounty(custom.getCoolingPerFinishedBounty());

            mergeRangeMap(base.getRenownRangePerRarity(), custom.getRenownRangePerRarity());
            mergeRangeMap(base.getReputationPerRarity(), custom.getReputationPerRarity());
            mergeFloatMap(base.getBaseRarityWeights(), custom.getBaseRarityWeights());
            mergeFloatMap(base.getRarityWeightChangePerRank(), custom.getRarityWeightChangePerRank());

            if (custom.getRarityRankFalloff() != null && custom.getRarityRankFalloff() > 0.0F) {
                base.setRarityRankFalloff(custom.getRarityRankFalloff());
            }

            if (!config.getRanks().isEmpty()) {
                base.setRanks(new ArrayList<>(config.getRanks()));
            }

            if (config.getEndlessRank() != null && config.getEndlessRank().getRenownCost() > 0) {
                base.setEndlessRank(config.getEndlessRank());
            }

            for (Map.Entry<String, List<String>> flavorEntry : custom.getFlavorLinesByType().entrySet()) {
                if (flavorEntry.getKey() != null && flavorEntry.getValue() != null) {
                    base.getFlavorLinesByType()
                            .computeIfAbsent(flavorEntry.getKey(), k -> new ArrayList<>())
                            .addAll(flavorEntry.getValue());
                }
            }
        }

        super.afterConfigsLoad(event);
    }

    private static void mergeRangeMap(
            Map<VaultGearRarity, IntRangeEntry> target,
            Map<VaultGearRarity, IntRangeEntry> source
    ) {
        for (Map.Entry<VaultGearRarity, IntRangeEntry> entry : source.entrySet()) {
            if (entry.getKey() != null && entry.getValue() != null) {
                target.put(entry.getKey(), entry.getValue());
            }
        }
    }

    private static void mergeFloatMap(
            Map<VaultGearRarity, Float> target,
            Map<VaultGearRarity, Float> source
    ) {
        for (Map.Entry<VaultGearRarity, Float> entry : source.entrySet()) {
            if (entry.getKey() != null && entry.getValue() != null) {
                target.put(entry.getKey(), entry.getValue());
            }
        }
    }
}