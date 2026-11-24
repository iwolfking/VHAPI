package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.bounty.BountyBoardRewardsConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BountyBoardRewardsConfig.Entry.class)
public interface BountyBoardRewardsConfigEntryAccessor {

    @Accessor("minLevel")
    int getMinLevel();

    @Accessor("minLevel")
    void setMinLevel(int minLevel);

    @Accessor("maxLevel")
    int getMaxLevel();

    @Accessor("maxLevel")
    void setMaxLevel(int maxLevel);

    @Accessor("minGreedTier")
    int getMinGreedTier();

    @Accessor("minGreedTier")
    void setMinGreedTier(int minGreedTier);

    @Accessor("scalesWithGreedTier")
    void setScalesWithGreedTier(boolean scalesWithGreedTier);

    @Accessor("identified")
    void setIdentified(boolean identified);
}