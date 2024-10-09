package xyz.iwolfking.vhapi.api.lib.config.loaders.gear;


import iskallia.vault.VaultMod;

import iskallia.vault.config.gear.VaultGearTierConfig;

import net.minecraftforge.fml.common.Mod;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.lib.loaders.VaultConfigDataLoader;


import java.util.HashMap;
@Mod.EventBusSubscriber(modid = "vhapi", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CustomVaultGearLoader extends VaultConfigDataLoader<VaultGearTierConfig> {


    public CustomVaultGearLoader(String namespace) {
        super(new VaultGearTierConfig(VaultMod.id("test")), "gear/gear_modifiers", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        VaultGearTierConfig.registerConfigs();
    }

}
