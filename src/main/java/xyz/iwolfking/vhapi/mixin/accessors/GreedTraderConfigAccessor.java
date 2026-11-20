package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.greed.GreedTraderConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import java.util.List;
import java.util.Map;

@Mixin(value = GreedTraderConfig.class, remap = false)
public interface GreedTraderConfigAccessor {

    @Accessor("pools")
    Map<String, List<GreedTraderConfig.PoolEntry>> getPoolsMap();

    @Accessor("roomPoolsByType")
    Map<String, List<GreedTraderConfig.WeightedRoomPool>> getRoomPoolsByType();

    @Accessor("modifierPools")
    Map<String, List<GreedTraderConfig.WeightedModifier>> getModifierPools();

}
