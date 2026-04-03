package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.UniqueDiscoveryConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = UniqueDiscoveryConfig.class, remap = false)
public interface UniqueDiscoveryConfigAccessor {
    @Accessor("discoveryHints")
    Map<String, String> getDiscoveryHints();
}
