package xyz.iwolfking.vhapi.api.loaders.bounty;

import iskallia.vault.config.bounty.RewardConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

public class BountyRewardsConfigLoader extends VaultConfigProcessor<RewardConfig> {

    public BountyRewardsConfigLoader() {
        super(new RewardConfig(), "bounty/rewards");
    }

    //TODO: Support editing Bounty rewards properly.
    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(RewardConfig config : this.CUSTOM_CONFIGS.values()) {
            ModConfigs.REWARD_CONFIG = config;
        }

    }
}
