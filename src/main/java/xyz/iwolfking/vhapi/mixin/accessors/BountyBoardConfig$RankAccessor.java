package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.bounty.BountyBoardConfig;
import iskallia.vault.looks.LooksCategory;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(value = BountyBoardConfig.Rank.class, remap = false)
public interface BountyBoardConfig$RankAccessor {

    @Accessor("renownCost")
    int getRenownCost();

    @Accessor("renownCost")
    void setRenownCost(int renownCost);

    @Accessor("rewardName")
    String getRewardName();

    @Accessor("rewardName")
    void setRewardName(String rewardName);

    @Accessor("rewards")
    List<ItemStack> getRewards();

    @Accessor("rewards")
    void setRewards(List<ItemStack> rewards);

    @Accessor("looks")
    LooksCategory getLooks();

    @Accessor("looks")
    void setLooks(LooksCategory looks);
}