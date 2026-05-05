package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.AugmentStationConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Map;

@Mixin(value = AugmentStationConfig.class, remap = false)
public interface AugmentStationConfigAccessor {
    @Accessor("themes")
    List<AugmentStationConfig.ThemeEntry> getThemes();

    @Accessor("recipes")
    Map<Integer, AugmentStationConfig.TierRecipe> getRecipes();
}
