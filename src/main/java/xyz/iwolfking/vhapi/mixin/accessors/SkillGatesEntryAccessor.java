package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.skill.SkillGates;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = SkillGates.Entry.class, remap = false)
public interface SkillGatesEntryAccessor {
    @Accessor("ignoreArrow")
    void setIgnoreArrow(boolean hideArrow);
}
