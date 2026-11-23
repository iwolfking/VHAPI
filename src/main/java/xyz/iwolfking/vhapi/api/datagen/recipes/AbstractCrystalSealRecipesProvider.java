package xyz.iwolfking.vhapi.api.datagen.recipes;

import iskallia.vault.config.AugmentStationConfig;
import iskallia.vault.config.entry.ItemEntry;
import iskallia.vault.config.entry.recipe.ConfigCrystalSealRecipe;
import iskallia.vault.config.recipe.CrystalSealRecipesConfig;
import iskallia.vault.gear.crafting.recipe.CrystalSealForgeRecipe;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.ItemStack;
import xyz.iwolfking.vhapi.api.datagen.AbstractVaultConfigDataProvider;
import xyz.iwolfking.vhapi.api.datagen.lib.BasicListBuilder;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.AugmentStationConfig$TierRecipeAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.AugmentStationConfigAccessor;

import java.util.*;
import java.util.function.Consumer;

public abstract class AbstractCrystalSealRecipesProvider extends AbstractVaultConfigDataProvider<AbstractCrystalSealRecipesProvider.Builder> {

    protected AbstractCrystalSealRecipesProvider(DataGenerator generator, String modid) {
        super(generator, modid, "vault_recipes/crystal_seal", Builder::new);
    }

    @Override
    public String getName() {
        return modid + " Crystal Seal Recipes Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<CrystalSealRecipesConfig> {
        private final List<ConfigCrystalSealRecipe> recipes = new ArrayList<>();

        private final List<AugmentStationConfig.ThemeEntry> themes = new LinkedList<>();

        public Builder() {
            super(CrystalSealRecipesConfig::new);
        }

        public Builder addSeal(ItemStack seal, int unlocklevel, int unlockGreedTier, Consumer<BasicListBuilder<ItemEntry>> ingredientConsumer) {
            BasicListBuilder<ItemEntry> builder = new BasicListBuilder<>();
            ingredientConsumer.accept(builder);
            ConfigCrystalSealRecipe recipe = new ConfigCrystalSealRecipe(seal, unlocklevel, unlockGreedTier);
            builder.build().forEach(itemEntry -> {
                recipe.addInput(itemEntry.createItemStack());
            });
            recipes.add(recipe);
            return this;
        }

        @Override
        public void configureConfig(CrystalSealRecipesConfig config) {
            config.getConfigRecipes().addAll(recipes);
        }

    }
}
