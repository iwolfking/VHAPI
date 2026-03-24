package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.AbilitiesGUIConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Map;

@Mixin(value = AbilitiesGUIConfig.class, remap = false)
public interface AbilitiesGUIConfigAccessor {
    @Accessor("styles")
    void setStyles(Map<String, AbilitiesGUIConfig.AbilityStyle> styles);

    @Accessor("lines")
    void setLines(List<AbilitiesGUIConfig.Line> lines);

    @Accessor("labels")
    void setLabels(List<AbilitiesGUIConfig.AbilityLabel> labels);
}
