package xyz.iwolfking.vhapi.api.datagen.recipes;

import iskallia.vault.config.entry.recipe.ConfigCatalystRecipe;
import iskallia.vault.config.entry.recipe.ConfigForgeRecipe;
import iskallia.vault.config.entry.recipe.ConfigGearRecipe;
import iskallia.vault.config.recipe.CatalystRecipesConfig;
import iskallia.vault.config.recipe.GearRecipesConfig;
import iskallia.vault.gear.crafting.recipe.VaultForgeRecipe;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import xyz.iwolfking.vhapi.api.datagen.AbstractVaultConfigDataProvider;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.api.datagen.lib.recipes.AbstractVaultRecipeBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractGearRecipesProvider extends AbstractVaultConfigDataProvider<AbstractGearRecipesProvider.Builder> {

    protected AbstractGearRecipesProvider(DataGenerator generator, String modid) {
        super(generator, modid, "vault_recipes/gear", Builder::new);
    }

    @Override
    public String getName() {
        return modid + " Gear Recipes Data Provider";
    }

    public static class Builder extends AbstractVaultRecipeBuilder<GearRecipesConfig, ConfigGearRecipe> {

        public Builder() {
            super(GearRecipesConfig::new);
        }

        public Builder addRecipe(ResourceLocation id, ItemStack output, Consumer<List<ItemStack>> inputs) {
            ConfigGearRecipe recipe = new ConfigGearRecipe(output);
            addAndConfigureRecipe(recipe, inputs, id);
            return this;
        }

        @Override
        public void configureConfig(GearRecipesConfig config) {
            config.getConfigRecipes().addAll(recipes);
        }

    }
}
