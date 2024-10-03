package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders;


import iskallia.vault.VaultMod;

import iskallia.vault.config.gear.VaultGearTierConfig;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;

import java.util.HashMap;

public class CustomVaultGearLoader extends VaultConfigDataLoader<VaultGearTierConfig> {

    public static final VaultGearTierConfig instance = new VaultGearTierConfig(VaultMod.id("test"));

    public CustomVaultGearLoader(String namespace) {
        super(instance, "gear_modifiers", new HashMap<>(), namespace);
    }

    @Override
    protected void performFinalActions() {
        VaultGearTierConfig.registerConfigs();
    }


}
