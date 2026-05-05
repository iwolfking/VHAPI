package xyz.iwolfking.vhapi.api.datagen.recipes;

import iskallia.vault.config.AugmentStationConfig;
import net.minecraft.data.DataGenerator;
import xyz.iwolfking.vhapi.api.datagen.AbstractVaultConfigDataProvider;
import xyz.iwolfking.vhapi.api.datagen.lib.BasicListBuilder;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.AugmentStationConfig$TierRecipeAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.AugmentStationConfigAccessor;

import java.util.*;
import java.util.function.Consumer;

public abstract class AbstractAugmentRecipesProvider extends AbstractVaultConfigDataProvider<AbstractAugmentRecipesProvider.Builder> {

    protected AbstractAugmentRecipesProvider(DataGenerator generator, String modid) {
        super(generator, modid, "vault_recipes/augment", Builder::new);
    }

    @Override
    public String getName() {
        return modid + " Augment Recipes Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<AugmentStationConfig> {
        private final Map<Integer, AugmentStationConfig.TierRecipe> recipes = new LinkedHashMap<>();

        private final List<AugmentStationConfig.ThemeEntry> themes = new LinkedList<>();

        public Builder() {
            super(AugmentStationConfig::new);
        }

        public Builder addTierRecipe(int tier, Consumer<BasicListBuilder<AugmentStationConfig.RecipeIngredient>> ingredientConsumer) {
            BasicListBuilder<AugmentStationConfig.RecipeIngredient> builder = new BasicListBuilder<>();
            ingredientConsumer.accept(builder);
            AugmentStationConfig.TierRecipe recipe = new AugmentStationConfig.TierRecipe();
            ((AugmentStationConfig$TierRecipeAccessor)recipe).setIngredients(builder.build());
            recipes.put(tier, recipe);
            return this;
        }

        public Builder addThemeGroup(String augment, int tier, boolean craftable) {
            themes.add(new AugmentStationConfig.ThemeEntry(augment, tier, craftable));
            return this;
        }

        @Override
        public void configureConfig(AugmentStationConfig config) {
            ((AugmentStationConfigAccessor)config).getRecipes().putAll(recipes);
            ((AugmentStationConfigAccessor)config).getThemes().addAll(themes);
        }

    }
}
