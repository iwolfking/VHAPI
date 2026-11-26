package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.core.vault.challenge.action.AddModifierChallengeAction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = AddModifierChallengeAction.Config.class, remap = false)
public interface AddModifierChallengeActionConfigAccessor {
    @Accessor("modifierId")
    void setModifierId(String modifierId);

    @Accessor("time")
    void setTime(int time);
}