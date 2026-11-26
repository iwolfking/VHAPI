package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.core.vault.challenge.action.GroupChallengeAction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = GroupChallengeAction.class, remap = false)
public interface GroupChallengeActionInvoker {
    @Invoker("<init>")
    static GroupChallengeAction create(GroupChallengeAction.Config config) {
        throw new AssertionError();
    }
}