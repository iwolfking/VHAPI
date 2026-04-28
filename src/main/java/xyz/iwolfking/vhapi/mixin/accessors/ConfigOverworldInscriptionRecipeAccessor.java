package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.entry.recipe.ConfigOverworldInscriptionRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ConfigOverworldInscriptionRecipe.class)
public interface ConfigOverworldInscriptionRecipeAccessor {
    @Accessor
    void setCreator(String creator);
}
