package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.entry.LevelEntryList;
import iskallia.vault.core.vault.challenge.action.PoolChallengeAction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = PoolChallengeAction.Config.class,remap = false)
public interface PoolChallengeActionAccessor {
    @Accessor("pools")
    void setPools(LevelEntryList<?> pools);
}
