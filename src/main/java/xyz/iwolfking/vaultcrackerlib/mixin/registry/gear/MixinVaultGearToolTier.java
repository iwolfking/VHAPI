package xyz.iwolfking.vaultcrackerlib.mixin.registry.gear;

import iskallia.vault.config.gear.VaultGearTierConfig;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vaultcrackerlib.api.registry.gear.CustomVaultGearRegistry;

import java.util.Map;

@Mixin(value = VaultGearTierConfig.class, remap = false)
public class MixinVaultGearToolTier {

    @Inject(method = "registerConfigs", at = @At("TAIL"), cancellable = true)
    private static void registerCustomGearConfigs(CallbackInfoReturnable<Map<ResourceLocation, VaultGearTierConfig>> cir) {
        Map<ResourceLocation, VaultGearTierConfig> gearTierConfigMap = cir.getReturnValue();
        CustomVaultGearRegistry.getCustomGearEntries().forEach(customVaultGearEntry -> {
            gearTierConfigMap.put(customVaultGearEntry.registryItem().getRegistryName(), (new VaultGearTierConfig(customVaultGearEntry.registryItem().getRegistryName()).readConfig()));
        });
        cir.setReturnValue(gearTierConfigMap);
    }
}

