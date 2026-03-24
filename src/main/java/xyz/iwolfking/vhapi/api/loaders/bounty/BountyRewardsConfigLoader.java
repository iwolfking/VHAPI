package xyz.iwolfking.vhapi.api.loaders.bounty;

import iskallia.vault.config.bounty.RewardConfig;
import iskallia.vault.config.entry.LevelEntryMap;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

public class BountyRewardsConfigLoader extends VaultConfigProcessor<RewardConfig> {

    public BountyRewardsConfigLoader() {
        super(new RewardConfig(), "bounty/rewards");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        this.CUSTOM_CONFIGS.forEach((resourceLocation, rewardConfig) -> {
            if(resourceLocation.getPath().contains("overwrite")) {
                ModConfigs.REWARD_CONFIG = rewardConfig;
            }
            else if(resourceLocation.getPath().contains("replace")) {
                rewardConfig.getPOOLS().forEach((s, rewardEntryLevelEntryMap) -> {
                    ModConfigs.REWARD_CONFIG.getPOOLS().put(s, rewardEntryLevelEntryMap);
                });
            }
            else {
                rewardConfig.getPOOLS().forEach((s, rewardEntryLevelEntryMap) -> {
                    if(ModConfigs.REWARD_CONFIG.getPOOLS().containsKey(s)) {
                        LevelEntryMap<RewardConfig.RewardEntry> rewardEntryLevelEntryMap1 = ModConfigs.REWARD_CONFIG.getPOOLS().get(s);
                        rewardEntryLevelEntryMap.forEach((integer, rewardEntry) -> {
                            if(rewardEntryLevelEntryMap1.containsKey(integer)) {
                                rewardEntry.itemPool.getPool().forEach(itemStackEntryEntry -> {
                                    rewardEntryLevelEntryMap1.get(integer).itemPool.addItemStack(itemStackEntryEntry.value.getMatchingStack());
                                });
                                rewardEntryLevelEntryMap1.get(integer).discoverModels.addAll(rewardEntry.discoverModels);
                                rewardEntryLevelEntryMap1.get(integer).vaultExp = rewardEntry.vaultExp;
                            }
                            else {
                                rewardEntryLevelEntryMap1.put(integer, rewardEntry);
                            }
                        });
                    }
                    else {
                        ModConfigs.REWARD_CONFIG.getPOOLS().put(s, rewardEntryLevelEntryMap);
                    }
                });
            }
        });
        super.afterConfigsLoad(event);
    }
}
