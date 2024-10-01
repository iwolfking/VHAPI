package xyz.iwolfking.vaultcrackerlib.api.patching.configs.lib.custom;

import iskallia.vault.config.SoulShardConfig;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.util.data.WeightedList;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.lib.SetInjector;

import java.util.ArrayList;
import java.util.List;

public class BlackMarketPatcher extends SetInjector<SoulShardConfig.Trades> {

    public static void addTradeToLevel(int level, SoulShardConfig.ShardTrade trade, int weight) {
        for(SoulShardConfig.Trades trades : ModConfigs.SOUL_SHARD.getTrades()) {
            if(trades.getMinLevel() == level) {
                trades.getShardTrades().add(trade, weight);
            }
        }
    }

    public static void removeTradeFromLevel(int level, ResourceLocation itemId) {
        List<WeightedList.Entry<SoulShardConfig.ShardTrade>> tradesToRemove = new ArrayList<>();
        WeightedList<SoulShardConfig.ShardTrade> listToRemoveFrom = null;
        for(SoulShardConfig.Trades trades : ModConfigs.SOUL_SHARD.getTrades()) {
            if(trades.getMinLevel() == level) {
                listToRemoveFrom = trades.getShardTrades();
                for(WeightedList.Entry<SoulShardConfig.ShardTrade> trade : trades.getShardTrades()) {
                    if(trade.value.getItem().getItem().getRegistryName().equals(itemId)) {
                        tradesToRemove.add(trade);
                    }
                }
            }
        }

        if(listToRemoveFrom != null) {
            listToRemoveFrom.removeAll(tradesToRemove);
        }

    }
}
