package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.VaultCrystalCatalystConfig;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;

@Mixin(value= VaultCrystalCatalystConfig.ModifierPool.class, remap = false)
public interface ModifierPoolAccessor {
    @Accessor("modifierIdList")
    public Set<ResourceLocation> getModifierIds();
}
