package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.core.vault.challenge.action.MultiplyActionsChallengeAction;
import javax.annotation.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = MultiplyActionsChallengeAction.Config.class, remap = false)
public interface MultiplyActionsChallengeActionConfigAccessor {
    @Accessor("name")
    void setName(@Nullable String name);
}