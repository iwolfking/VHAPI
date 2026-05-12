package xyz.iwolfking.vhapi.mixin.recipes;

import iskallia.vault.init.ModConfigs;
import iskallia.vault.init.ModItems;
import iskallia.vault.item.crystal.CrystalData;
import iskallia.vault.recipe.anvil.AnvilRecipe;
import iskallia.vault.recipe.anvil.SealAnvilRecipe;
import iskallia.vault.recipe.anvil.VanillaAnvilRecipe;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.recipe.vanilla.IJeiAnvilRecipe;
import mezz.jei.api.recipe.vanilla.IVanillaRecipeFactory;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import xyz.iwolfking.vhapi.mixin.accessors.VaultCrystalConfigAccessor;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = SealAnvilRecipe.class, remap = false)
public abstract class MixinSealAnvilRecipe extends VanillaAnvilRecipe {


    @Overwrite
    public void onRegisterJEI(IRecipeRegistration registry) {
        IVanillaRecipeFactory factory = registry.getVanillaRecipeFactory();
        List<IJeiAnvilRecipe> anvilRecipes = new ArrayList<>();

        ItemStack baseCrystal = new ItemStack(ModItems.VAULT_CRYSTAL);

        ((VaultCrystalConfigAccessor)ModConfigs.VAULT_CRYSTAL).getSeals().forEach((id, sealEntries) -> {
            Item sealItem = ForgeRegistries.ITEMS.getValue(id);

            if(sealItem != null) {
                ItemStack sealStack = sealItem.getDefaultInstance();
                ItemStack outputCrystal = baseCrystal.copy();
                CrystalData data = CrystalData.read(outputCrystal);

                if (ModConfigs.VAULT_CRYSTAL.applySeal(baseCrystal, sealStack, outputCrystal, data)) {
                    data.write(outputCrystal);

                    anvilRecipes.add(factory.createAnvilRecipe(
                            List.of(baseCrystal),
                            List.of(sealStack),
                            List.of(outputCrystal)
                    ));
                }
            }


        });

        registry.addRecipes(RecipeTypes.ANVIL, anvilRecipes);
    }
}
