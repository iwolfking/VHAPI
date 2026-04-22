package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.greed.GreedTraderConfig;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = GreedTraderConfig.PoolEntry.class, remap = false)
public interface GreedTraderConfigPoolEntry {
}
