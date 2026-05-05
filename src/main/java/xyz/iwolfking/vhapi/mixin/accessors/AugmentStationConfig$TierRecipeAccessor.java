package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.AugmentStationConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Map;

@Mixin(value = AugmentStationConfig.TierRecipe.class, remap = false)
public interface AugmentStationConfig$TierRecipeAccessor {
    @Accessor("ingredients")
    List<AugmentStationConfig.RecipeIngredient> getIngredients();

    @Accessor("ingredients")
    void setIngredients(List<AugmentStationConfig.RecipeIngredient> ingredients);
}
