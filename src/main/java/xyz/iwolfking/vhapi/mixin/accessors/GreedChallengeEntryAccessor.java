package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.greed.GreedChallengeEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = GreedChallengeEntry.class, remap = false)
public interface GreedChallengeEntryAccessor {
    @Accessor("displayName")
    void setDisplayName(String displayName);

    @Accessor("description")
    void setDescription(String displayName);

    @Accessor("challengeCrystalId")
    void setId(String id);

    @Accessor("minTier")
    void setMinTier(int minTier);

    @Accessor("maxTier")
    void setMaxTier(int maxTier);
}
