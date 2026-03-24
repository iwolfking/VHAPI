package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.card.BoosterPackConfig;
import iskallia.vault.config.card.CardDeckConfig;
import iskallia.vault.config.entry.IntRangeEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;

@Mixin(value = BoosterPackConfig.CardConfig.class, remap = false)
public interface CardConfigAccessor {
    @Accessor("groups")
    Set<String> getGroups();

}
