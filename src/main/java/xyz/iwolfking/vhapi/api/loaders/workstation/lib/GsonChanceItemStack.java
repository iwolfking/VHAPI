package xyz.iwolfking.vhapi.api.loaders.workstation.lib;

import com.google.gson.annotations.Expose;
import iskallia.vault.config.entry.ChanceItemStackEntry;
import net.minecraft.world.item.ItemStack;

public class GsonChanceItemStack {
    @Expose
    private final ItemStack stack;
    @Expose
    private final int minCount;
    @Expose
    private final int maxCount;

    @Expose
    private final float chance;

    public GsonChanceItemStack(ItemStack stack, int minCount, int maxCount, float chance) {
        this.stack = stack;
        this.minCount = minCount;
        this.maxCount = maxCount;
        this.chance = chance;
    }

    public ChanceItemStackEntry getStack() {
        return new ChanceItemStackEntry(stack, minCount, maxCount, chance);
    }
}
