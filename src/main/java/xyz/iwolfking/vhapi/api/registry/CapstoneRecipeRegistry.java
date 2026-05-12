package xyz.iwolfking.vhapi.api.registry;

import iskallia.vault.VaultMod;
import iskallia.vault.core.vault.modifier.VaultModifierStack;
import iskallia.vault.core.vault.modifier.registry.VaultModifierRegistry;
import iskallia.vault.core.vault.modifier.spi.VaultModifier;
import iskallia.vault.init.ModItems;
import iskallia.vault.item.crystal.CrystalData;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.recipe.vanilla.IVanillaRecipeFactory;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import xyz.iwolfking.vhapi.integration.jevh.VHAPIJEIPlugin;
import xyz.iwolfking.vhapi.integration.jevh.categories.CrystalWorkbenchRecipeCategory;
import xyz.iwolfking.vhapi.integration.jevh.lib.CrystalWorkbenchRecipe;

import java.util.Collections;
import java.util.List;

public class CapstoneRecipeRegistry {

    public static void register(IRecipeRegistration registry, Item capstoneItem, ResourceLocation modifierId) {
        ItemStack inputCrystal = new ItemStack(ModItems.VAULT_CRYSTAL);
        ItemStack capstoneStack = new ItemStack(capstoneItem);

        ItemStack outputCrystal = inputCrystal.copy();
        CrystalData crystalData = CrystalData.read(outputCrystal);

        VaultModifierRegistry.getOpt(modifierId).ifPresent(modifier -> {
            VaultModifierStack modifierStack = VaultModifierStack.of(modifier);

            if (crystalData.addModifierByCrafting(modifierStack, false, true)) {
                crystalData.addModifierByCrafting(modifierStack, false, false);
                crystalData.getProperties().setUnmodifiable(true);
            }
        });
        
        crystalData.write(outputCrystal);

        registry.addRecipes(List.of(new CrystalWorkbenchRecipe(capstoneStack, outputCrystal)), CrystalWorkbenchRecipeCategory.UID);
    }
}