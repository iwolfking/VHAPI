package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.core.vault.modifier.registry.VaultModifierRegistry;
import iskallia.vault.core.vault.modifier.spi.VaultModifier;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = VaultModifierRegistry.class, remap = false)
public interface VaultModifierRegistyAccessor {
    @Accessor("VAULT_MODIFIER_MAP")
    public static Map<ResourceLocation, VaultModifier<?>> getVaultModifierMap() {
        return null;
    }

}
