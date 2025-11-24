package xyz.iwolfking.vhapi.api.datagen.recipes;

import iskallia.vault.config.entry.recipe.ConfigGearRecipe;
import iskallia.vault.config.entry.recipe.ConfigInscriptionRecipe;
import iskallia.vault.config.recipe.GearRecipesConfig;
import iskallia.vault.config.recipe.InscriptionRecipesConfig;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import xyz.iwolfking.vhapi.api.datagen.AbstractVaultConfigDataProvider;
import xyz.iwolfking.vhapi.api.datagen.lib.recipes.AbstractVaultRecipeBuilder;

import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractInscriptionRecipesProvider extends AbstractVaultConfigDataProvider<AbstractInscriptionRecipesProvider.Builder> {

    protected AbstractInscriptionRecipesProvider(DataGenerator generator, String modid) {
        super(generator, modid, "vault_recipes/inscription", Builder::new);
    }

    @Override
    public String getName() {
        return modid + " Inscriptions Recipes Data Provider";
    }

    public static class Builder extends AbstractVaultRecipeBuilder<InscriptionRecipesConfig, ConfigInscriptionRecipe> {

        public Builder() {
            super(InscriptionRecipesConfig::new);
        }

        public Builder addRecipe(ResourceLocation id, ItemStack output, Consumer<List<ItemStack>> inputs) {
            ConfigInscriptionRecipe recipe = new ConfigInscriptionRecipe();
            addAndConfigureRecipe(recipe, inputs, id, output);
            return this;
        }

        @Override
        public void configureConfig(InscriptionRecipesConfig config) {
            config.getConfigRecipes().addAll(recipes);
        }

    }
}
