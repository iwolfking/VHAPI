package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.core.vault.challenge.action.ChallengeAction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = ChallengeAction.class, remap = false)
public interface ChallengeActionAccessor<C extends ChallengeAction.Config> {
    @Accessor("config")
    C getConfig();

    @Mutable
    @Accessor("config")
    void setConfig(C config);

    @Accessor("populated")
    boolean isPopulated();

    @Accessor("populated")
    void setPopulated(boolean populated);

    @Invoker("<init>")
    static <C extends ChallengeAction.Config> ChallengeAction<C> create(C config) {
        throw new AssertionError();
    }
}