package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.TrinketConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = TrinketConfig.Trinket.class, remap = false)
public interface TrinketConfigTrinketAccessor {
    @Accessor("trinketConfig")
    void setTrinketConfig(Object config);

    @Accessor("minCraftedUses")
    void setMinCraftedUses(int minCraftedUses);

    @Accessor("maxCraftedUses")
    void setMaxCraftedUses(int maxCraftedUses);

}
