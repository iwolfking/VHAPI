package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.shops;

import iskallia.vault.config.SoulShardConfig;
import iskallia.vault.config.tool.ToolPulverizingConfig;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.util.data.WeightedList;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;

import java.util.HashMap;
import java.util.Map;

public class NormalBlackMarketConfigLoader extends VaultConfigDataLoader<SoulShardConfig> {
    public NormalBlackMarketConfigLoader(String namespace) {
        super(new SoulShardConfig(), "market/standard", new HashMap<>(), namespace);
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

    }
}
