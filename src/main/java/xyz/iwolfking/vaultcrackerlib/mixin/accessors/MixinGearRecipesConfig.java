package xyz.iwolfking.vaultcrackerlib.mixin.accessors;

import iskallia.vault.config.recipe.GearRecipesConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(value = GearRecipesConfig.class, remap = false)
public interface MixinGearRecipesConfig {
    @Accessor
    List<GearRecipesConfig> getGearRecipes();
}
