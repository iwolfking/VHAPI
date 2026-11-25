package xyz.iwolfking.vhapi.api.extensions.general;

import iskallia.vault.config.recipe.ForgeRecipeType;
import iskallia.vault.config.recipe.ForgeRecipesConfig;
import iskallia.vault.gear.crafting.recipe.VaultForgeRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import xyz.iwolfking.vhapi.api.extensions.lib.IEnumFactory;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class ForgeRecipeTypeFactory implements IEnumFactory {
    public static final ForgeRecipeTypeFactory INSTANCE = new ForgeRecipeTypeFactory();

    private ForgeRecipeTypeFactory() {
    }

    @Override
    public String getTargetEnumClassName() {
        return ForgeRecipeType.class.getName();
    }

    // Declared fields in ForgeRecipeType:
    // 0 BiFunction -> recipeClassCtor
    // 0 Supplier   -> configSupplier
    // 1 Supplier   -> recipeGetter
    @Override
    public Object[] getFieldSpec() {
        return new Object[]{
                0, BiFunction.class,
                0, Supplier.class,
                1, Supplier.class
        };
    }

    public ForgeRecipeType create(
            String enumName,
            BiFunction<ResourceLocation, ItemStack, ? extends VaultForgeRecipe> recipeClassCtor,
            Supplier<ForgeRecipesConfig<?, ?>> configSupplier,
            Class<?>... switchUsers
    ) {
        BiFunction<ResourceLocation, ItemStack, ? extends VaultForgeRecipe> ctor =
                Objects.requireNonNull(recipeClassCtor, "recipeClassCtor");
        Supplier<ForgeRecipesConfig<?, ?>> configSup =
                Objects.requireNonNull(configSupplier, "configSupplier");
        Supplier<Function<ResourceLocation, VaultForgeRecipe>> recipeGetter = () -> {
            ForgeRecipesConfig<?, ?> config = configSup.get();
            Objects.requireNonNull(config, "ForgeRecipesConfig supplier returned null for " + enumName);
            return config::getRecipe;
        };

        return appendConstant(
                enumName,
                new Object[]{ctor, configSup, recipeGetter},
                switchUsers
        );
    }

    public ForgeRecipeType create(
            ResourceLocation enumId,
            BiFunction<ResourceLocation, ItemStack, ? extends VaultForgeRecipe> recipeClassCtor,
            Supplier<ForgeRecipesConfig<?, ?>> configSupplier,
            Class<?>... switchUsers
    ) {
        return create(toInternalName(enumId), recipeClassCtor, configSupplier, switchUsers);
    }
}