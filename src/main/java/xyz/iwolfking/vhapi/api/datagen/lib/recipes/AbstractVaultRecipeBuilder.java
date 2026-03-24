package xyz.iwolfking.vhapi.api.datagen.lib.recipes;

import iskallia.vault.config.entry.ItemEntry;
import iskallia.vault.config.entry.recipe.ConfigForgeRecipe;
import iskallia.vault.config.recipe.ForgeRecipesConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.ConfigForgeRecipeAccessor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class AbstractVaultRecipeBuilder<T extends ForgeRecipesConfig<?, ?>, R extends ConfigForgeRecipe<?>> extends VaultConfigBuilder<T> {

    protected List<R> recipes = new ArrayList<>();

    public AbstractVaultRecipeBuilder(Supplier<T> factory) {
        super(factory);
    }

    protected void addAndConfigureRecipe(R recipe, Consumer<List<ItemStack>> inputsConsumer, ResourceLocation id, ItemStack output) {
        addInputs(recipe, inputsConsumer);
        if(output != null) {
            assignOutput(recipe, output);
        }
        assignId(recipe, id);
        recipes.add(recipe);
    }

    protected void addAndConfigureRecipe(R recipe, Consumer<List<ItemStack>> inputsConsumer, ResourceLocation id) {
        addAndConfigureRecipe(recipe, inputsConsumer, id, null);
    }

    private void addInputs(R recipe, Consumer<List<ItemStack>> inputsConsumer) {
        List<ItemStack> inputs = new ArrayList<>();
        inputsConsumer.accept(inputs);
        inputs.forEach(recipe::addInput);
    }

    private void assignId(R recipe, ResourceLocation id) {
        ((ConfigForgeRecipeAccessor)recipe).setId(id);
    }

    private void assignOutput(R recipe, ItemStack output) {
        ((ConfigForgeRecipeAccessor)recipe).setOutput(new ItemEntry(output));
    }
}
