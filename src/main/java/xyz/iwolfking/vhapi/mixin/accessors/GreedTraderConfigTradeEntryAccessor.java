package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.greed.GreedTraderConfig;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = GreedTraderConfig.TradeEntry.class, remap = false)
public interface GreedTraderConfigTradeEntryAccessor {
    @Accessor("poolId")
    void setPoolId(String poolId);

    @Accessor("type")
    void setType(String type);

    @Accessor("weight")
    void setWeight(int weight);

    @Accessor("itemStack")
    void setItemStack(ItemStack stack);

    @Accessor("minAmount")
    void setMinAmount(int minAmount);

    @Accessor("maxAmount")
    void setMaxAmount(int maxAmount);

    @Accessor("minCoinCost")
    void setMinCoinCost(int minCoinCost);

    @Accessor("maxCoinCost")
    void setMaxCoinCost(int maxCoinCost);
}
