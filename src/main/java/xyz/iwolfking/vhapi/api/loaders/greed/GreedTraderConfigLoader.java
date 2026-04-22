package xyz.iwolfking.vhapi.api.loaders.greed;

import iskallia.vault.config.greed.GreedTraderConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.GreedTraderConfigAccessor;

public class GreedTraderConfigLoader extends VaultConfigProcessor<GreedTraderConfig> {
    public GreedTraderConfigLoader() {
        super(new GreedTraderConfig(), "greed/trader");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        this.CUSTOM_CONFIGS.forEach((resourceLocation, greedTraderConfig) -> {
            ((GreedTraderConfigAccessor)greedTraderConfig).getTierPoolsMap().forEach((tier, tradeEntries) -> {
                tradeEntries.forEach(tradeEntry -> {
                    ModConfigs.GREED_TRADER.addTradeEntry(tier, tradeEntry);
                });
            });

            ((GreedTraderConfigAccessor)ModConfigs.GREED_TRADER).getPoolsMap().putAll(((GreedTraderConfigAccessor)greedTraderConfig).getPoolsMap());

            ((GreedTraderConfigAccessor)greedTraderConfig).getRoomPoolsByType().forEach((s, weightedRoomPools) -> {
                if(((GreedTraderConfigAccessor) ModConfigs.GREED_TRADER).getRoomPoolsByType().containsKey(s)) {
                    ((GreedTraderConfigAccessor) ModConfigs.GREED_TRADER).getRoomPoolsByType().get(s).addAll(weightedRoomPools);
                }
                else {
                    ((GreedTraderConfigAccessor) ModConfigs.GREED_TRADER).getRoomPoolsByType().put(s, weightedRoomPools);
                }
            });

            ((GreedTraderConfigAccessor)greedTraderConfig).getModifierPools().forEach((s, weightedModifiers) -> {
                if(((GreedTraderConfigAccessor) ModConfigs.GREED_TRADER).getModifierPools().containsKey(s)) {
                    ((GreedTraderConfigAccessor) ModConfigs.GREED_TRADER).getModifierPools().get(s).addAll(weightedModifiers);
                }
                else {
                    ((GreedTraderConfigAccessor) ModConfigs.GREED_TRADER).getModifierPools().put(s, weightedModifiers);
                }
            });

            ((GreedTraderConfigAccessor)ModConfigs.GREED_TRADER).getQuestsList().addAll(((GreedTraderConfigAccessor)greedTraderConfig).getQuestsList());
            ((GreedTraderConfigAccessor)ModConfigs.GREED_TRADER).getChallengesList().addAll(((GreedTraderConfigAccessor)greedTraderConfig).getChallengesList());

            ((GreedTraderConfigAccessor)ModConfigs.GREED_TRADER).getMaxQuestsPerTier().putAll(((GreedTraderConfigAccessor) greedTraderConfig).getMaxQuestsPerTier());
            ((GreedTraderConfigAccessor)ModConfigs.GREED_TRADER).getMaxChallengesPerTier().putAll(((GreedTraderConfigAccessor) greedTraderConfig).getMaxChallengesPerTier());

        });

        super.afterConfigsLoad(event);
    }
}
