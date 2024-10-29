package xyz.iwolfking.vhapi.api.loaders.gear;


import iskallia.vault.VaultMod;

import iskallia.vault.config.gear.VaultGearTierConfig;

import net.minecraftforge.fml.common.Mod;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

@Mod.EventBusSubscriber(modid = "vhapi", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CustomVaultGearLoader extends VaultConfigProcessor<VaultGearTierConfig> {


    public CustomVaultGearLoader() {
        super(new VaultGearTierConfig(VaultMod.id("test")), "gear/gear_modifiers");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        VaultGearTierConfig.registerConfigs();
        super.afterConfigsLoad(event);
    }

}
