package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.block.entity.challenge.raid.action.PoolChallengeAction;
import iskallia.vault.config.entry.LevelEntryList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = PoolChallengeAction.Config.class,remap = false)
public interface PoolChallengeActionAccessor {
    @Accessor("pools")
    void setPools(LevelEntryList<?> pools);
}
