package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.skill.base.LearnableSkill;
import iskallia.vault.skill.prestige.core.PrestigePower;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = LearnableSkill.class, remap = false)
public interface LearnableSkillAccessor {
    @Accessor("regretCost")
    void setRegretCost(int learnCost);

    @Accessor("regretCost")
    int getRegretCost();

    @Accessor("unlockLevel")
    void setUnlockLevel(int level);

    @Accessor("unlockLevel")
    int getUnlockLevel();

    @Accessor("learnPointCost")
    void setLearnCost(int learnCost);

    @Accessor("learnPointCost")
    int getLearnCost();

}
