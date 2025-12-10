package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.core.vault.challenge.action.ChallengeAction;
import iskallia.vault.core.vault.challenge.action.GroupChallengeAction;
import iskallia.vault.core.world.roll.IntRoll;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = GroupChallengeAction.Config.class,remap = false)
public interface GroupChallengeActionConfigAccessor {
    @Accessor("children")
    void setChildren(Map<ChallengeAction<?>, IntRoll> children);
}
