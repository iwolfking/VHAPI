package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.gear.trinket.TrinketEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = TrinketEffect.Config.class, remap = false)
public interface TrinketEffectConfigAccessor {
    @Accessor("curiosSlot")
    void setCuriosSlot(String curiosSlot);
}
