package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.AbilitiesConfig;
import iskallia.vault.config.card.CardDeckConfig;
import iskallia.vault.config.entry.IntRangeEntry;
import iskallia.vault.skill.tree.AbilityTree;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = CardDeckConfig.Entry.class, remap = false)
public interface CardDeckConfigEntryAccessor {
    @Accessor("essence")
    void setEssence(IntRangeEntry essence);

    @Accessor("socketCount")
    void setSocketCount(IntRangeEntry socketCount);

    @Accessor("essence")
    IntRangeEntry getEssence();

    @Accessor("socketCount")
    IntRangeEntry getSocketCount();
}
