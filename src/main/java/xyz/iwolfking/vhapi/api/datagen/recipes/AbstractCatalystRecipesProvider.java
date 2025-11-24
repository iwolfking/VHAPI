package xyz.iwolfking.vhapi.api.datagen.recipes;

import iskallia.vault.config.entry.recipe.ConfigCatalystRecipe;
import iskallia.vault.config.recipe.CatalystRecipesConfig;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import xyz.iwolfking.vhapi.api.datagen.AbstractVaultConfigDataProvider;
import xyz.iwolfking.vhapi.api.datagen.lib.recipes.AbstractVaultRecipeBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractCatalystRecipesProvider extends AbstractVaultConfigDataProvider<AbstractCatalystRecipesProvider.Builder> {

    protected AbstractCatalystRecipesProvider(DataGenerator generator, String modid) {
        super(generator, modid, "vault_recipes/catalyst", Builder::new);
    }

    @Override
    public String getName() {
        return modid + " Catalyst Recipes Data Provider";
    }

    public static class Builder extends AbstractVaultRecipeBuilder<CatalystRecipesConfig, ConfigCatalystRecipe> {

        public Builder() {
            super(CatalystRecipesConfig::new);
        }


        public Builder addRecipe(ResourceLocation id, ResourceLocation pool, int minSize, int maxSize, Consumer<List<ResourceLocation>> modifiersConsumer, Consumer<List<ItemStack>> inputsConsumer) {
            List<ResourceLocation> modifiers = new ArrayList<>();
            modifiersConsumer.accept(modifiers);
            ConfigCatalystRecipe recipe = new ConfigCatalystRecipe(id, pool, minSize, maxSize, modifiers.toArray(modifiers.toArray(new ResourceLocation[0])));
            addAndConfigureRecipe(recipe, inputsConsumer, id);
            return this;
        }

        @Override
        public void configureConfig(CatalystRecipesConfig config) {
            config.getConfigRecipes().addAll(recipes);
        }

    }
}
