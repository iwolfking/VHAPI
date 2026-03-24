package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.skill.base.LearnableSkill;
import iskallia.vault.skill.base.SpecializedSkill;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import java.util.List;

@Mixin(value = SpecializedSkill.class, remap = false)
public interface SpecializedSkillAccessor {
    @Accessor("specializations")
    List<LearnableSkill> getSpecializations();

    @Accessor("specializations")
    void setSpecializations(List<LearnableSkill> specializations);
}
