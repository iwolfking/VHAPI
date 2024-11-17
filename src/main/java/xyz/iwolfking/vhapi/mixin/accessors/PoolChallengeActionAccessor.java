package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.block.entity.challenge.raid.action.ChallengeAction;
import iskallia.vault.block.entity.challenge.raid.action.PoolChallengeAction;
import iskallia.vault.config.RaidActionsConfig;
import iskallia.vault.core.util.WeightedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = PoolChallengeAction.Config.class,remap = false)
public interface PoolChallengeActionAccessor {
    @Accessor("pool")
    WeightedList<ChallengeAction<?>> getPool();
}
