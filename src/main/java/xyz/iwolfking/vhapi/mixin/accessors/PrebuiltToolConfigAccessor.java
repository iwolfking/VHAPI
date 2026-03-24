package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.AbilitiesGUIConfig;
import iskallia.vault.config.PreBuiltToolConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Map;

@Mixin(value = PreBuiltToolConfig.class, remap = false)
public interface PrebuiltToolConfigAccessor {
    @Accessor("tools")
    void setTools(Map<String, Map<String, Map<String, Object>>> styles);

    @Accessor("tools")
    Map<String, Map<String, Map<String, Object>>> getTools();
}
