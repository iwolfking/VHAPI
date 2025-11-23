package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.gear.VaultGearTierConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = VaultGearTierConfig.ModifierTier.class, remap = false)
public interface ModifierGearTierAccessor {

    @Accessor("maxLevel")
    void setMaxLevel(int maxLevel);

    @Accessor("modifierTier")
    void setModifierTier(int tier);
}
