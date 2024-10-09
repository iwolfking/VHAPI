package xyz.iwolfking.vaultcrackerlib.mixin.registry.gear;


import com.llamalad7.mixinextras.sugar.Local;
import iskallia.vault.config.Config;
import iskallia.vault.config.GearModelRollRaritiesConfig;
import iskallia.vault.gear.VaultGearRarity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vaultcrackerlib.api.data.CustomGearModelRolls;
import xyz.iwolfking.vaultcrackerlib.api.registry.VaultGearRegistry;

import xyz.iwolfking.vaultcrackerlib.api.registry.gear.CustomVaultGearRegistryEntry;


import java.util.List;
import java.util.Map;

@Mixin(value = GearModelRollRaritiesConfig.class, remap = false)
public abstract class MixinGearModelRollRaritiesConfig extends Config {

    @Inject(method = "getRolls", at = @At("HEAD"), cancellable = true)
    private void getRollsHook(CallbackInfoReturnable<Map<VaultGearRarity, List<String>>> cir, @Local ItemStack stack) {
        System.out.println("GET ROLL");
        System.out.println(stack.getItem().getRegistryName());
        if(CustomGearModelRolls.CUSTOM_MODEL_ROLLS_MAP.containsKey(stack.getItem().getRegistryName())) {
            System.out.println("Found in map!");
            cir.setReturnValue(CustomGearModelRolls.CUSTOM_MODEL_ROLLS_MAP.get(stack.getItem().getRegistryName()));
        }
    }


}
