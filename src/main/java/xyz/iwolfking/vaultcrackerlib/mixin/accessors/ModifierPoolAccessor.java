package xyz.iwolfking.vaultcrackerlib.mixin.accessors;

import net.minecraft.resources.ResourceLocation;
import org.checkerframework.checker.units.qual.A;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;

@Mixin(targets = "iskallia.vault.config.VaultCrystalCatalystConfig$ModifierPool", remap = false)
public interface ModifierPoolAccessor {
    @Accessor("modifierIdList")
    public Set<ResourceLocation> getModifierIds();
}
