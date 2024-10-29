package xyz.iwolfking.vhapi.api.loaders.shops;

import iskallia.vault.config.SoulShardConfig;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.util.data.WeightedList;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

import java.util.HashMap;
import java.util.Map;

public class NormalBlackMarketConfigLoader extends VaultConfigProcessor<SoulShardConfig> {
    public NormalBlackMarketConfigLoader() {
        super(new SoulShardConfig(), "market/standard");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ResourceLocation key : this.CUSTOM_CONFIGS.keySet()) {
            SoulShardConfig config = CUSTOM_CONFIGS.get(key);
            Map<Integer, WeightedList<SoulShardConfig.ShardTrade>> levelToTradesMap = new HashMap<>();
            for(SoulShardConfig.Trades trades : config.getTrades()) {
                levelToTradesMap.put(trades.getMinLevel(), trades.getShardTrades());
            }
            if(key.getPath().contains("overwrite")) {
                ModConfigs.SOUL_SHARD = config;
            } else if (key.getPath().contains("remove")) {
                for(SoulShardConfig.Trades trades : ModConfigs.SOUL_SHARD.getTrades()) {
                    trades.getShardTrades().removeAll(levelToTradesMap.get(trades.getMinLevel()));
                }
            }
            else {
                for(SoulShardConfig.Trades trades : ModConfigs.SOUL_SHARD.getTrades()) {
                    trades.getShardTrades().addAll(levelToTradesMap.get(trades.getMinLevel()));
                }
            }
        }
        super.afterConfigsLoad(event);
    }
}
