package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.gear.EtchingTierConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = EtchingTierConfig.EtchingModifierTier.class, remap = false)
public interface EtchingModifierTierAccessor {
    @Accessor("maxGreedTier")
    void setMaxGreedTier(int maxGreedTier);

    @Accessor("modifierTier")
    void setModifierTier(int modifierTier);
}
