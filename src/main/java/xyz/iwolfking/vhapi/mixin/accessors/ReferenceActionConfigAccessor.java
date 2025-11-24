package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.block.entity.challenge.raid.action.ReferenceChallengeAction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = ReferenceChallengeAction.Config.class,remap = false)
public interface ReferenceActionConfigAccessor {
    @Accessor("path")
    void setPath(String path);

}
