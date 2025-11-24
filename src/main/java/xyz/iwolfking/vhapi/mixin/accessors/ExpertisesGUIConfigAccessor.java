package xyz.iwolfking.vhapi.mixin.accessors;


import iskallia.vault.config.ExpertisesGUIConfig;
import iskallia.vault.config.entry.SkillStyle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.HashMap;

@Mixin(value = ExpertisesGUIConfig.class, remap = false)
public interface ExpertisesGUIConfigAccessor {
    @Accessor("styles")
    void setStyles(HashMap<String, SkillStyle> styles);
}
