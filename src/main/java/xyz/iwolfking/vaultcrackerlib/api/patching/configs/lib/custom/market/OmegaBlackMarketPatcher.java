package xyz.iwolfking.vaultcrackerlib.api.patching.configs.lib.custom.market;

import iskallia.vault.config.OmegaSoulShardConfig;
import iskallia.vault.config.SoulShardConfig;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.util.data.WeightedList;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.lib.SetInjector;

import java.util.ArrayList;
import java.util.List;

public class OmegaBlackMarketPatcher<T> extends SetInjector<OmegaSoulShardConfig.Trades> {

    public static void addTradeToLevel(int level, OmegaSoulShardConfig.ShardTrade trade, int weight) {
        for(OmegaSoulShardConfig.Trades trades : ModConfigs.OMEGA_SOUL_SHARD.getTrades()) {
            if(trades.getMinLevel() == level) {
                trades.getShardTrades().add(trade, weight);
            }
        }
    }

    public static void removeTradeFromLevel(int level, ResourceLocation itemId) {
        List<WeightedList.Entry<OmegaSoulShardConfig.ShardTrade>> tradesToRemove = new ArrayList<>();
        WeightedList<OmegaSoulShardConfig.ShardTrade> listToRemoveFrom = null;
        for(OmegaSoulShardConfig.Trades trades : ModConfigs.OMEGA_SOUL_SHARD.getTrades()) {
            if(trades.getMinLevel() == level) {
                listToRemoveFrom = trades.getShardTrades();
                for(WeightedList.Entry<OmegaSoulShardConfig.ShardTrade> trade : trades.getShardTrades()) {
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
