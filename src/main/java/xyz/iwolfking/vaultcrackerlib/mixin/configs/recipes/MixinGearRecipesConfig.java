package xyz.iwolfking.vaultcrackerlib.mixin.configs.recipes;

import iskallia.vault.config.entry.recipe.ConfigGearRecipe;
import iskallia.vault.config.recipe.GearRecipesConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.Patchers;

import java.util.List;

@Mixin(value = GearRecipesConfig.class, remap = false)
public abstract class MixinGearRecipesConfig {
    @Shadow @Final private List<ConfigGearRecipe> gearRecipes;

    @Inject(method = "getConfigRecipes", at = @At("HEAD"))
    public void getConfigRecipes(CallbackInfoReturnable<List<ConfigGearRecipe>> cir) {
    }
}
