package xyz.iwolfking.vhapi.api.datagen.recipes;

import iskallia.vault.config.entry.recipe.ConfigInscriptionRecipe;
import iskallia.vault.config.entry.recipe.ConfigJewelCraftingRecipe;
import iskallia.vault.config.recipe.InscriptionRecipesConfig;
import iskallia.vault.config.recipe.JewelCraftingRecipesConfig;
import iskallia.vault.gear.crafting.recipe.JewelCraftingRecipe;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import xyz.iwolfking.vhapi.api.datagen.AbstractVaultConfigDataProvider;
import xyz.iwolfking.vhapi.api.datagen.lib.recipes.AbstractVaultRecipeBuilder;

import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractJewelCraftingRecipesProvider extends AbstractVaultConfigDataProvider<AbstractJewelCraftingRecipesProvider.Builder> {

    protected AbstractJewelCraftingRecipesProvider(DataGenerator generator, String modid) {
        super(generator, modid, "vault_recipes/jewel", Builder::new);
    }

    @Override
    public String getName() {
        return modid + " Jewel Crafting Recipes Data Provider";
    }

    public static class Builder extends AbstractVaultRecipeBuilder<JewelCraftingRecipesConfig, ConfigJewelCraftingRecipe> {

        public Builder() {
            super(JewelCraftingRecipesConfig::new);
        }

        public Builder addRecipe(ResourceLocation id, ResourceLocation jewelAttributeId, int size, Consumer<List<ItemStack>> inputs) {
            ConfigJewelCraftingRecipe recipe = new ConfigJewelCraftingRecipe(jewelAttributeId, size);
            addAndConfigureRecipe(recipe, inputs, id);
            return this;
        }

        @Override
        public void configureConfig(JewelCraftingRecipesConfig config) {
            config.getConfigRecipes().addAll(recipes);
        }

    }
}
