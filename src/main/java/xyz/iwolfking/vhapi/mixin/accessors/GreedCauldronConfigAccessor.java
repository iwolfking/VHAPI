package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.greed.GreedCauldronConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(value = GreedCauldronConfig.class, remap = false)
public interface GreedCauldronConfigAccessor {
    @Accessor("demands")
    List<GreedCauldronConfig.DemandEntry> getDemands();
}
