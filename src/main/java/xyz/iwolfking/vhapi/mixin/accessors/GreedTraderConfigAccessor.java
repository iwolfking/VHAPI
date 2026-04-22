package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.greed.GreedChallengeEntry;
import iskallia.vault.config.greed.GreedQuestEntry;
import iskallia.vault.config.greed.GreedTraderConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import java.util.List;
import java.util.Map;

@Mixin(value = GreedTraderConfig.class, remap = false)
public interface GreedTraderConfigAccessor {
    @Accessor("globalCoinCost")
    int getGlobalCoinCost();

    @Accessor("questRerollCoinCost")
    int getQuestRerollCoinCost();

    @Accessor("challengeRebuyCoinCost")
    int getChallengeRebuyCoinCost();

    @Accessor("tierPools")
    Map<Integer, List<GreedTraderConfig.TradeEntry>> getTierPoolsMap();

    @Accessor("pools")
    Map<String, List<GreedTraderConfig.PoolEntry>> getPoolsMap();

    @Accessor("roomPoolsByType")
    Map<String, List<GreedTraderConfig.WeightedRoomPool>> getRoomPoolsByType();

    @Accessor("modifierPools")
    Map<String, List<GreedTraderConfig.WeightedModifier>> getModifierPools();

    @Accessor("quests")
    List<GreedQuestEntry> getQuestsList();

    @Accessor("maxQuestsPerTier")
    Map<Integer, Integer> getMaxQuestsPerTier();

    @Accessor("challenges")
    List<GreedChallengeEntry> getChallengesList();

    @Accessor("maxChallengesPerTier")
    Map<Integer, Integer>  getMaxChallengesPerTier();
}
