package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.core.vault.challenge.action.AddMobsChallengeAction;
import iskallia.vault.core.world.data.entity.PartialEntity;
import iskallia.vault.core.world.roll.IntRoll;
import javax.annotation.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = AddMobsChallengeAction.Config.class, remap = false)
public interface AddMobsChallengeActionConfigAccessor {
    @Accessor("name")
    void setName(@Nullable String name);

    @Accessor("entity")
    void setEntity(PartialEntity entity);

    @Accessor("spawner")
    void setSpawner(String spawner);

    @Accessor("count")
    void setCount(IntRoll count);
}