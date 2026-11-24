package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.bounty.BountyBoardConfig;
import iskallia.vault.config.entry.IntRangeEntry;
import iskallia.vault.gear.VaultGearRarity;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.data.DataGenerator;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.BountyBoardConfig$RankAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.BountyBoardConfigAccessor;

public abstract class AbstractBountyBoardProvider extends AbstractVaultConfigDataProvider<AbstractBountyBoardProvider.Builder> {
    protected AbstractBountyBoardProvider(DataGenerator generator, String modid) {
        super(generator, modid, "bounty/board", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Bounty Board Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<BountyBoardConfig> {
        @Nullable
        public Integer offeredScrolls;
        @Nullable
        public Integer baseActiveBounties;
        @Nullable
        public Float refreshBasePrice;
        @Nullable
        public Float refreshPriceGrowth;
        @Nullable
        public Integer heatPerRefresh;
        @Nullable
        public Integer heatPerAbandon;
        @Nullable
        public Integer coolingPerFinishedBounty;
        public final Map<VaultGearRarity, IntRangeEntry> renownRangePerRarity = new LinkedHashMap<>();
        public final Map<VaultGearRarity, IntRangeEntry> reputationPerRarity = new LinkedHashMap<>();
        public final Map<VaultGearRarity, Float> baseRarityWeights = new LinkedHashMap<>();
        public final Map<VaultGearRarity, Float> rarityWeightChangePerRank = new LinkedHashMap<>();
        @Nullable
        public Float rarityRankFalloff;
        public final List<BountyBoardConfig.Rank> ranks = new ArrayList<>();
        @Nullable
        public BountyBoardConfig.Rank endlessRank;
        public final Map<String, List<String>> flavorLinesByType = new LinkedHashMap<>();

        public Builder() {
            super(BountyBoardConfig::new);
        }

        public Builder addRank(BountyBoardConfig.Rank rank, @Nullable Consumer<BountyBoardConfig$RankAccessor> rankCustomizer) {
            if (rankCustomizer != null) {
                rankCustomizer.accept((BountyBoardConfig$RankAccessor) rank);
            }
            this.ranks.add(rank);
            return this;
        }

        public Builder addFlavorLines(String bountyType, List<String> lines) {
            this.flavorLinesByType.computeIfAbsent(bountyType, k -> new ArrayList<>()).addAll(lines);
            return this;
        }

        @Override
        protected void configureConfig(BountyBoardConfig config) {
            BountyBoardConfigAccessor accessor = (BountyBoardConfigAccessor) config;

            if (this.offeredScrolls != null) {
                accessor.setOfferedScrolls(this.offeredScrolls);
            }
            if (this.baseActiveBounties != null) {
                accessor.setBaseActiveBounties(this.baseActiveBounties);
            }
            if (this.refreshBasePrice != null) {
                accessor.setRefreshBasePrice(this.refreshBasePrice);
            }
            if (this.refreshPriceGrowth != null) {
                accessor.setRefreshPriceGrowth(this.refreshPriceGrowth);
            }
            if (this.heatPerRefresh != null) {
                accessor.setHeatPerRefresh(this.heatPerRefresh);
            }
            if (this.heatPerAbandon != null) {
                accessor.setHeatPerAbandon(this.heatPerAbandon);
            }
            if (this.coolingPerFinishedBounty != null) {
                accessor.setCoolingPerFinishedBounty(this.coolingPerFinishedBounty);
            }
            if (!this.renownRangePerRarity.isEmpty()) {
                accessor.setRenownRangePerRarity(new LinkedHashMap<>(this.renownRangePerRarity));
            }
            if (!this.reputationPerRarity.isEmpty()) {
                accessor.setReputationPerRarity(new LinkedHashMap<>(this.reputationPerRarity));
            }
            if (!this.baseRarityWeights.isEmpty()) {
                accessor.setBaseRarityWeights(new LinkedHashMap<>(this.baseRarityWeights));
            }
            if (!this.rarityWeightChangePerRank.isEmpty()) {
                accessor.setRarityWeightChangePerRank(new LinkedHashMap<>(this.rarityWeightChangePerRank));
            }
            if (this.rarityRankFalloff != null) {
                accessor.setRarityRankFalloff(this.rarityRankFalloff);
            }
            if (!this.ranks.isEmpty()) {
                accessor.setRanks(new ArrayList<>(this.ranks));
            }
            if (this.endlessRank != null) {
                accessor.setEndlessRank(this.endlessRank);
            }
            if (!this.flavorLinesByType.isEmpty()) {
                Map<String, List<String>> copy = new LinkedHashMap<>();
                for (Map.Entry<String, List<String>> entry : this.flavorLinesByType.entrySet()) {
                    copy.put(entry.getKey(), new ArrayList<>(entry.getValue()));
                }
                accessor.setFlavorLinesByType(copy);
            }
        }
    }
}