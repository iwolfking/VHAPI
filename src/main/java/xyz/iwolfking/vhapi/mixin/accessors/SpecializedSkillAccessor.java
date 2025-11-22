package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.skill.ability.effect.spi.core.CooldownSkill;
import iskallia.vault.skill.base.LearnableSkill;
import iskallia.vault.skill.base.SpecializedSkill;
import iskallia.vault.skill.base.TieredSkill;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = SpecializedSkill.class, remap = false)
public interface SpecializedSkillAccessor {

}
