package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.bounty;

import iskallia.vault.config.ShopPedestalConfig;
import iskallia.vault.config.bounty.RewardConfig;
import iskallia.vault.config.card.CardDeckConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.CardDeckConfigAccessor;

import java.util.HashMap;
import java.util.Map;

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
