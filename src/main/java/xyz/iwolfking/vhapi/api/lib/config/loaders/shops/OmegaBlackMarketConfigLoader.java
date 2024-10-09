package xyz.iwolfking.vhapi.api.lib.config.loaders.shops;

import iskallia.vault.config.OmegaSoulShardConfig;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.util.data.WeightedList;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.lib.loaders.VaultConfigDataLoader;

import java.util.HashMap;
import java.util.Map;

public class OmegaBlackMarketConfigLoader extends VaultConfigDataLoader<OmegaSoulShardConfig> {
    public OmegaBlackMarketConfigLoader(String namespace) {
        super(new OmegaSoulShardConfig(), "market/omega", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ResourceLocation key : this.CUSTOM_CONFIGS.keySet()) {
            OmegaSoulShardConfig config = CUSTOM_CONFIGS.get(key);
            Map<Integer, WeightedList<OmegaSoulShardConfig.ShardTrade>> levelToTradesMap = new HashMap<>();
            for(OmegaSoulShardConfig.Trades trades : config.getTrades()) {
                levelToTradesMap.put(trades.getMinLevel(), trades.getShardTrades());
            }
            if(key.getPath().contains("overwrite")) {
                ModConfigs.OMEGA_SOUL_SHARD = config;
            } else if (key.getPath().contains("remove")) {
                for(OmegaSoulShardConfig.Trades trades : ModConfigs.OMEGA_SOUL_SHARD.getTrades()) {
                    trades.getShardTrades().removeAll(levelToTradesMap.get(trades.getMinLevel()));
                }
            }
            else {
                for(OmegaSoulShardConfig.Trades trades : ModConfigs.OMEGA_SOUL_SHARD.getTrades()) {
                    trades.getShardTrades().addAll(levelToTradesMap.get(trades.getMinLevel()));
                }
            }
        }

    }
}
