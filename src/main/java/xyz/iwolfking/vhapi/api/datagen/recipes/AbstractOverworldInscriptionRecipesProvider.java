package xyz.iwolfking.vhapi.api.datagen.recipes;

import iskallia.vault.config.entry.recipe.ConfigOverworldInscriptionRecipe;
import iskallia.vault.config.recipe.OverworldInscriptionRecipesConfig;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import xyz.iwolfking.vhapi.api.datagen.AbstractVaultConfigDataProvider;
import xyz.iwolfking.vhapi.api.datagen.lib.recipes.AbstractVaultRecipeBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.ConfigOverworldInscriptionRecipeAccessor;

import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractOverworldInscriptionRecipesProvider extends AbstractVaultConfigDataProvider<AbstractOverworldInscriptionRecipesProvider.Builder> {

    protected AbstractOverworldInscriptionRecipesProvider(DataGenerator generator, String modid) {
        super(generator, modid, "vault_recipes/overworld_inscription", Builder::new);
    }

    @Override
    public String getName() {
        return modid + " Overworld Inscriptions Recipes Data Provider";
    }

    public static class Builder extends AbstractVaultRecipeBuilder<OverworldInscriptionRecipesConfig, ConfigOverworldInscriptionRecipe> {

        public Builder() {
            super(OverworldInscriptionRecipesConfig::new);
        }

        public Builder addRecipe(ResourceLocation id, ItemStack output, Consumer<List<ItemStack>> inputs, String creator) {
            ConfigOverworldInscriptionRecipe recipe = new ConfigOverworldInscriptionRecipe();
            ((ConfigOverworldInscriptionRecipeAccessor) recipe).setCreator(creator);
            addAndConfigureRecipe(recipe, inputs, id, output);
            return this;
        }

        @Override
        public void configureConfig(OverworldInscriptionRecipesConfig config) {
            config.getConfigRecipes().addAll(recipes);
        }

    }
}
