package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.core.data.key.registry.KeyRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = KeyRegistry.class, remap = false)
public interface KeyRegistryAccessor {
    @Accessor("locked")
    public void setLocked(boolean locked);
}
