package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.card.BoosterPackConfig;
import iskallia.vault.config.entry.IntRangeEntry;
import iskallia.vault.core.card.CardEntry;
import iskallia.vault.core.util.WeightedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = BoosterPackConfig.BoosterPackEntry.class, remap = false)
public interface BoosterPackEntryAccessor {
    @Accessor("roll")
    WeightedList<Integer> getRolls();

    @Accessor("tier")
    WeightedList<Integer> getTiers();

    @Accessor("color")
    WeightedList<CardEntry.Color> getColor();

}
