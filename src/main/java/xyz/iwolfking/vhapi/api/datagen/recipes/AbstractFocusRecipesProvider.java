package xyz.iwolfking.vhapi.api.datagen.recipes;

import iskallia.vault.config.entry.ItemEntry;
import iskallia.vault.config.entry.recipe.ConfigFocusRecipe;
import iskallia.vault.config.recipe.ForgedFociRecipesConfig;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.ItemStack;
import xyz.iwolfking.vhapi.api.datagen.AbstractVaultConfigDataProvider;
import xyz.iwolfking.vhapi.api.datagen.lib.BasicListBuilder;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractFocusRecipesProvider extends AbstractVaultConfigDataProvider<AbstractFocusRecipesProvider.Builder> {

    protected AbstractFocusRecipesProvider(DataGenerator generator, String modid) {
        super(generator, modid, "vault_recipes/forged_foci", Builder::new);
    }

    @Override
    public String getName() {
        return modid + " Forged Foci Recipes Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<ForgedFociRecipesConfig> {
        private final List<ConfigFocusRecipe> recipes = new ArrayList<>();

        public Builder() {
            super(ForgedFociRecipesConfig::new);
        }

        public Builder addFocus(ItemStack seal, Consumer<BasicListBuilder<ItemEntry>> ingredientConsumer) {
            BasicListBuilder<ItemEntry> builder = new BasicListBuilder<>();
            ingredientConsumer.accept(builder);
            ConfigFocusRecipe recipe = new ConfigFocusRecipe(seal);
            builder.build().forEach(itemEntry -> recipe.addInput(itemEntry.createItemStack()));
            recipes.add(recipe);
            return this;
        }

        @Override
        public void configureConfig(ForgedFociRecipesConfig config) {
            config.getConfigRecipes().addAll(recipes);
        }

    }
}
