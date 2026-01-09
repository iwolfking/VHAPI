package xyz.iwolfking.vhapi.api.datagen.recipes;

import iskallia.vault.config.entry.recipe.ConfigCatalystRecipe;
import iskallia.vault.config.entry.recipe.ConfigDeckRecipe;
import iskallia.vault.config.recipe.CatalystRecipesConfig;
import iskallia.vault.config.recipe.DeckCraftingRecipesConfig;
import iskallia.vault.init.ModItems;
import iskallia.vault.item.CardDeckItem;
import iskallia.vault.item.DeckSocketItem;
import iskallia.vault.task.Task;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import xyz.iwolfking.vhapi.api.datagen.AbstractVaultConfigDataProvider;
import xyz.iwolfking.vhapi.api.datagen.lib.recipes.AbstractVaultRecipeBuilder;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractDeckRecipesProvider extends AbstractVaultConfigDataProvider<AbstractDeckRecipesProvider.Builder> {

    protected AbstractDeckRecipesProvider(DataGenerator generator, String modid) {
        super(generator, modid, "vault_recipes/deck", Builder::new);
    }

    @Override
    public String getName() {
        return modid + " Card Deck Recipes Data Provider";
    }

    public static class Builder extends AbstractVaultRecipeBuilder<DeckCraftingRecipesConfig, ConfigDeckRecipe> {

        public Builder() {
            super(DeckCraftingRecipesConfig::new);
        }


        public Builder addRecipe(ResourceLocation id, String deckId, Consumer<List<ItemStack>> inputsConsumer) {
            return addRecipe(id, deckId, null, null, "decks", inputsConsumer, ModItems.CARD_DECK);
        }

        public Builder addRecipe(ResourceLocation id, String deckId, Task task, Consumer<List<ItemStack>> inputsConsumer) {
            return addRecipe(id, deckId, null, task, "decks", inputsConsumer, ModItems.CARD_DECK);
        }

        public Builder addRecipe(ResourceLocation id, String coreId, String rollId, Consumer<List<ItemStack>> inputsConsumer) {
            return addRecipe(id, coreId, rollId, null, "cores", inputsConsumer, ModItems.DECK_SOCKET);
        }

        public Builder addRecipe(ResourceLocation id, String coreId, String rollId, Task task, Consumer<List<ItemStack>> inputsConsumer) {
            return addRecipe(id, coreId, rollId, task, "cores", inputsConsumer, ModItems.DECK_SOCKET);
        }

        public Builder addRecipe(ResourceLocation id, String deckId, @Nullable String rollId, @Nullable Task task, String tab, Consumer<List<ItemStack>> inputsConsumer, Item itemType) {
            ItemStack output = new ItemStack(itemType);
            if(output.getItem() instanceof CardDeckItem) {
                CardDeckItem.setId(output, deckId);
            }
            else if(output.getItem() instanceof DeckSocketItem) {
                DeckSocketItem.setModifierPool(output, deckId, rollId);
            }
            ConfigDeckRecipe recipe = task == null ? new ConfigDeckRecipe(output) : new ConfigDeckRecipe(output, task);
            recipe.setTab(tab);
            addAndConfigureRecipe(recipe, inputsConsumer, id);
            return this;
        }

        @Override
        public void configureConfig(DeckCraftingRecipesConfig config) {
            config.getConfigRecipes().addAll(recipes);
        }

    }
}
