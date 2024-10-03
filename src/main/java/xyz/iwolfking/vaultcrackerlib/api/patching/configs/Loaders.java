package xyz.iwolfking.vaultcrackerlib.api.patching.configs;

import iskallia.vault.config.recipe.GearRecipesConfig;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.CustomVaultGearLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.CustomVaultGearWorkbenchLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;

import java.util.HashMap;

public class Loaders {

    public static final GearRecipesConfig gearRecipesConfigInstance = new GearRecipesConfig();



    public static final CustomVaultGearLoader CUSTOM_VAULT_GEAR_LOADER = new CustomVaultGearLoader("the_vault");
    public static final CustomVaultGearWorkbenchLoader CUSTOM_VAULT_GEAR_WORKBENCH_LOADER = new CustomVaultGearWorkbenchLoader("the_vault");

    public static final VaultConfigDataLoader<GearRecipesConfig> GEAR_RECIPES_LOADER = new VaultConfigDataLoader<GearRecipesConfig>(gearRecipesConfigInstance, "gear_recipes", new HashMap<>(), "the_vault");

}
