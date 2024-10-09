package xyz.iwolfking.vhapi.api.lib.config.loaders.bounty;

import iskallia.vault.config.bounty.RewardConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.lib.loaders.VaultConfigDataLoader;

import java.util.HashMap;

public class BountyRewardsConfigLoader extends VaultConfigDataLoader<RewardConfig> {

    public BountyRewardsConfigLoader(String namespace) {
        super(new RewardConfig(), "bounty/rewards", new HashMap<>(), namespace);
    }

    //TODO: Support editing Bounty rewards properly.
    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(RewardConfig config : this.CUSTOM_CONFIGS.values()) {
            ModConfigs.REWARD_CONFIG = config;
        }

    }
}
