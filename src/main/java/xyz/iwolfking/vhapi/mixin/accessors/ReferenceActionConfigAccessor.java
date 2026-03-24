package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.core.vault.challenge.action.ReferenceChallengeAction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = ReferenceChallengeAction.Config.class,remap = false)
public interface ReferenceActionConfigAccessor {
    @Accessor("path")
    void setPath(String path);

}
