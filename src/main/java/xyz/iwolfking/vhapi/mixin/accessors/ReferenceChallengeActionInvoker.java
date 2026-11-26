package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.core.vault.challenge.action.ReferenceChallengeAction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = ReferenceChallengeAction.class, remap = false)
public interface ReferenceChallengeActionInvoker {
    @Invoker("<init>")
    static ReferenceChallengeAction create(ReferenceChallengeAction.Config config) {
        throw new AssertionError();
    }
}