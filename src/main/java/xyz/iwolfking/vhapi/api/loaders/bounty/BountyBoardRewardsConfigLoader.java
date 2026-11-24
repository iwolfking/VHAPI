package xyz.iwolfking.vhapi.api.loaders.bounty;

import iskallia.vault.config.bounty.BountyBoardRewardsConfig;
import iskallia.vault.gear.VaultGearRarity;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.BountyBoardRewardsConfigAccessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BountyBoardRewardsConfigLoader extends VaultConfigProcessor<BountyBoardRewardsConfig> {
    public BountyBoardRewardsConfigLoader() {
        super(new BountyBoardRewardsConfig(), "bounty/rewards");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        Map<VaultGearRarity, List<BountyBoardRewardsConfig.Entry>> baseRewards =
                ((BountyBoardRewardsConfigAccessor) ModConfigs.BOUNTY_BOARD_REWARDS).getRewardsByRarity();

        for (BountyBoardRewardsConfig config : this.CUSTOM_CONFIGS.values()) {
            Map<VaultGearRarity, List<BountyBoardRewardsConfig.Entry>> customRewards =
                    ((BountyBoardRewardsConfigAccessor) config).getRewardsByRarity();

            for (Map.Entry<VaultGearRarity, List<BountyBoardRewardsConfig.Entry>> rarityEntry : customRewards.entrySet()) {
                VaultGearRarity rarity = rarityEntry.getKey();
                List<BountyBoardRewardsConfig.Entry> customEntries = rarityEntry.getValue();
                if (rarity == null || customEntries == null) {
                    continue;
                }

                List<BountyBoardRewardsConfig.Entry> targetList =
                        baseRewards.computeIfAbsent(rarity, k -> new ArrayList<>());
                targetList.addAll(customEntries);
            }
        }

        super.afterConfigsLoad(event);
    }
}