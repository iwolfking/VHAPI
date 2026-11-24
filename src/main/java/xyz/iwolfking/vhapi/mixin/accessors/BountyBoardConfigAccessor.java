package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.bounty.BountyBoardConfig;
import iskallia.vault.config.entry.IntRangeEntry;
import iskallia.vault.gear.VaultGearRarity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Map;

@Mixin(value = BountyBoardConfig.class, remap = false)
public interface BountyBoardConfigAccessor {

    @Accessor("offeredScrolls")
    int getOfferedScrolls();

    @Accessor("offeredScrolls")
    void setOfferedScrolls(int offeredScrolls);

    @Accessor("baseActiveBounties")
    int getBaseActiveBounties();

    @Accessor("baseActiveBounties")
    void setBaseActiveBounties(int baseActiveBounties);

    @Accessor("refreshBasePrice")
    float getRefreshBasePrice();

    @Accessor("refreshBasePrice")
    void setRefreshBasePrice(float refreshBasePrice);

    @Accessor("refreshPriceGrowth")
    float getRefreshPriceGrowth();

    @Accessor("refreshPriceGrowth")
    void setRefreshPriceGrowth(float refreshPriceGrowth);

    @Accessor("heatPerRefresh")
    int getHeatPerRefresh();

    @Accessor("heatPerRefresh")
    void setHeatPerRefresh(int heatPerRefresh);

    @Accessor("heatPerAbandon")
    int getHeatPerAbandon();

    @Accessor("heatPerAbandon")
    void setHeatPerAbandon(int heatPerAbandon);

    @Accessor("coolingPerFinishedBounty")
    int getCoolingPerFinishedBounty();

    @Accessor("coolingPerFinishedBounty")
    void setCoolingPerFinishedBounty(int coolingPerFinishedBounty);

    @Accessor("renownRangePerRarity")
    Map<VaultGearRarity, IntRangeEntry> getRenownRangePerRarity();

    @Accessor("renownRangePerRarity")
    void setRenownRangePerRarity(Map<VaultGearRarity, IntRangeEntry> renownRangePerRarity);

    @Accessor("reputationPerRarity")
    Map<VaultGearRarity, IntRangeEntry> getReputationPerRarity();

    @Accessor("reputationPerRarity")
    void setReputationPerRarity(Map<VaultGearRarity, IntRangeEntry> reputationPerRarity);

    @Accessor("baseRarityWeights")
    Map<VaultGearRarity, Float> getBaseRarityWeights();

    @Accessor("baseRarityWeights")
    void setBaseRarityWeights(Map<VaultGearRarity, Float> baseRarityWeights);

    @Accessor("rarityWeightChangePerRank")
    Map<VaultGearRarity, Float> getRarityWeightChangePerRank();

    @Accessor("rarityWeightChangePerRank")
    void setRarityWeightChangePerRank(Map<VaultGearRarity, Float> rarityWeightChangePerRank);

    @Accessor("rarityRankFalloff")
    Float getRarityRankFalloff();

    @Accessor("rarityRankFalloff")
    void setRarityRankFalloff(Float rarityRankFalloff);

    @Accessor("ranks")
    List<BountyBoardConfig.Rank> getRanks();

    @Accessor("ranks")
    void setRanks(List<BountyBoardConfig.Rank> ranks);

    @Accessor("endlessRank")
    BountyBoardConfig.Rank getEndlessRank();

    @Accessor("endlessRank")
    void setEndlessRank(BountyBoardConfig.Rank endlessRank);

    @Accessor("flavorLinesByType")
    Map<String, List<String>> getFlavorLinesByType();

    @Accessor("flavorLinesByType")
    void setFlavorLinesByType(Map<String, List<String>> flavorLinesByType);
}