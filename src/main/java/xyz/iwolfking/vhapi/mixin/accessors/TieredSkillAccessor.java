package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.skill.base.LearnableSkill;
import iskallia.vault.skill.base.TieredSkill;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(value = TieredSkill.class, remap = false)
public interface TieredSkillAccessor {
    @Accessor("maxLearnableTier")
    void setMaxLearnableTier(int tier);

    @Accessor("tiers")
    void setTiers(List<LearnableSkill> tiers);

}
