package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.greed.GreedCauldronConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = GreedCauldronConfig.DemandEntry.class, remap = false)
public interface DemandEntryAccessor {
    @Accessor("item")
    String getItem();

    @Accessor("coinOutput")
    Integer getCoinOutput();

    @Accessor("minAmount")
    int getMinAmount();

    @Accessor("maxAmount")
    int getMaxAmount();
}
