package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.core.vault.challenge.action.ForfeitChallengeAction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = ForfeitChallengeAction.Config.class, remap = false)
public interface ForfeitChallengeActionConfigAccessor {
    @Accessor("loot")
    void setLoot(boolean loot);
}