package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.altar.VaultAltarIngredientsConfig;
import iskallia.vault.config.altar.entry.AltarIngredientEntry;
import iskallia.vault.config.entry.LevelEntryMap;
import iskallia.vault.util.data.WeightedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = VaultAltarIngredientsConfig.class, remap = false)
public interface VaultAltarIngredientsConfigAccessor {
    @Accessor("LEVELS")
    public LevelEntryMap<Map<String, WeightedList<AltarIngredientEntry>>> getLevels();
}
