package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.core.vault.challenge.action.PoolChallengeAction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = PoolChallengeAction.Config.LevelPool.class, remap = false)
public interface LevelPoolAccessor {
    @Accessor("level")
    int vhapi$getLevel();

    @Accessor("level")
    void vhapi$setLevel(int level);
}