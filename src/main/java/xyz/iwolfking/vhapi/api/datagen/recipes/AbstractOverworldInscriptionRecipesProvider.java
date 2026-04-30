package xyz.iwolfking.vhapi.api.datagen.recipes;

import iskallia.vault.config.entry.recipe.ConfigOverworldInscriptionRecipe;
import iskallia.vault.config.recipe.OverworldInscriptionRecipesConfig;
import iskallia.vault.init.ModItems;
import iskallia.vault.item.OverworldInscriptionItem;
import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
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

        public Builder addRecipe(ResourceLocation id, Consumer<List<ItemStack>> inputs, ResourceLocation templateID, String name, String creator) {
            ItemStack inscription = new ItemStack(ModItems.OVERWORLD_INSCRIPTION);
            OverworldInscriptionItem.setColor(inscription, 3118792);
            OverworldInscriptionItem.setStructureId(inscription, templateID);
            inscription.setHoverName(new TextComponent(name).withStyle(Style.EMPTY.withColor(0x2F96C8)));
            ConfigOverworldInscriptionRecipe recipe = new ConfigOverworldInscriptionRecipe();
            ((ConfigOverworldInscriptionRecipeAccessor) recipe).setCreator(creator);

            addAndConfigureRecipe(recipe, inputs, id, inscription);
            return this;
        }

        @Override
        public void configureConfig(OverworldInscriptionRecipesConfig config) {
            config.getConfigRecipes().addAll(recipes);
        }

    }
}
