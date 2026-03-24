package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.core.vault.challenge.action.TileRewardChallengeAction;
import iskallia.vault.core.world.data.tile.PartialTile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = TileRewardChallengeAction.Config.class,remap = false)
public interface TileRewardChallengeActionConfigAccessor {
    @Accessor("tile")
    void setTile(PartialTile tile);

    @Accessor("name")
    void setName(String name);

    @Accessor("count")
    void setCount(int count);
}
