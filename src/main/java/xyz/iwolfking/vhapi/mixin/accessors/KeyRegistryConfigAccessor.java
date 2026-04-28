package xyz.iwolfking.vhapi.mixin.accessors;

import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@org.spongepowered.asm.mixin.Mixin(iskallia.vault.config.core.KeyRegistryConfig.class)
public interface KeyRegistryConfigAccessor<T> {
    @Accessor
    List<T> getKeys();
}
