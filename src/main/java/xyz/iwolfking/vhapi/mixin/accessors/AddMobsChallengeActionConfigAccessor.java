package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.block.entity.challenge.raid.action.AddMobsChallengeAction;
import iskallia.vault.core.world.data.entity.PartialEntity;
import iskallia.vault.core.world.roll.IntRoll;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = AddMobsChallengeAction.Config.class,remap = false)
public interface AddMobsChallengeActionConfigAccessor {
    @Accessor("entity")
    void setEntity(PartialEntity entity);

    @Accessor("name")
    void setName(String name);

    @Accessor("spawner")
    void setSpawner(String spawner);

    @Accessor("count")
    void setCount(IntRoll count);
}
