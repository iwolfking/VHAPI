package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.ChallengeActionsConfig;
import iskallia.vault.core.vault.challenge.action.ChallengeAction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = ChallengeActionsConfig.class,remap = false)
public interface RaidActionsConfigAccessor {
    @Accessor("values")
    Map<String, ChallengeAction<?>> getValues();
}
