package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.core.vault.challenge.action.PoolChallengeAction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = PoolChallengeAction.class, remap = false)
public interface PoolChallengeActionInvoker {
    @Invoker("<init>")
    static PoolChallengeAction create(PoolChallengeAction.Config config) {
        throw new AssertionError();
    }
}