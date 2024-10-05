package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear;


import iskallia.vault.VaultMod;

import iskallia.vault.config.gear.VaultGearTierConfig;

import net.minecraftforge.fml.common.Mod;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;


import java.util.HashMap;
@Mod.EventBusSubscriber(modid = "vaultcrackerlib", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CustomVaultGearLoader extends VaultConfigDataLoader<VaultGearTierConfig> {

    public static final VaultGearTierConfig instance = new VaultGearTierConfig(VaultMod.id("test"));

    public CustomVaultGearLoader(String namespace) {
        super(instance, "gear_modifiers", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        VaultGearTierConfig.registerConfigs();
    }

}
