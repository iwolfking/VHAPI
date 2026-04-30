package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.core.KeyRegistryConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(value = KeyRegistryConfig.class, remap = false)
public interface KeyRegistryConfigAccessor<T> {
    @Accessor
    List<T> getKeys();
}
