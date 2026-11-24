package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.bounty.BountyBoardRewardsConfig;
import iskallia.vault.gear.VaultGearRarity;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.ItemStack;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.BountyBoardRewardsConfigAccessor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractBountyBoardRewardsProvider extends AbstractVaultConfigDataProvider<AbstractBountyBoardRewardsProvider.Builder> {
    protected AbstractBountyBoardRewardsProvider(DataGenerator generator, String modid) {
        super(generator, modid, "bounty/rewards", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Bounty Board Rewards Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<BountyBoardRewardsConfig> {
        public final Map<VaultGearRarity, List<BountyBoardRewardsConfig.Entry>> rewardsByRarity = new LinkedHashMap<>();

        public Builder() {
            super(BountyBoardRewardsConfig::new);
        }

        public Builder addReward(VaultGearRarity rarity, BountyBoardRewardsConfig.Entry entry) {
            this.rewardsByRarity.computeIfAbsent(rarity, k -> new ArrayList<>()).add(entry);
            return this;
        }

        public Builder addReward(VaultGearRarity rarity, ItemStack stack, int minAmount, int maxAmount) {
            return addReward(rarity, new BountyBoardRewardsConfig.Entry(stack, minAmount, maxAmount));
        }

        @Override
        protected void configureConfig(BountyBoardRewardsConfig config) {
            Map<VaultGearRarity, List<BountyBoardRewardsConfig.Entry>> copy = new LinkedHashMap<>();
            for (Map.Entry<VaultGearRarity, List<BountyBoardRewardsConfig.Entry>> e : this.rewardsByRarity.entrySet()) {
                copy.put(e.getKey(), new ArrayList<>(e.getValue()));
            }
            ((BountyBoardRewardsConfigAccessor) config).setRewardsByRarity(copy);
        }
    }
}