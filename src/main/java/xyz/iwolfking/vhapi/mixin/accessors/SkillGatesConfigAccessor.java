package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.PreBuiltToolConfig;
import iskallia.vault.config.SkillGatesConfig;
import iskallia.vault.skill.SkillGates;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = SkillGatesConfig.class, remap = false)
public interface SkillGatesConfigAccessor {
    @Accessor("SKILL_GATES")
    void setGates(SkillGates gates);
}
