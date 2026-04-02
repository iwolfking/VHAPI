package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.skill.expertise.type.FarmerTwerker;
import iskallia.vault.skill.prestige.core.PrestigePower;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = PrestigePower.class, remap = false)
public interface PrestigePowerAccessor {
    @Accessor("learnKnowledgeCost")
    void setKnowledgeCost(int knowledgeCost);

    @Accessor("requiredGreedTier")
    void setTierLock(int tierLock);

    @Accessor("tier")
    void setTier(int tier);

}
