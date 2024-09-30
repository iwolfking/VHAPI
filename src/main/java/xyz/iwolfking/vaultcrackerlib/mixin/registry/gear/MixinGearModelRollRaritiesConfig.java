package xyz.iwolfking.vaultcrackerlib.mixin.registry.gear;

import com.google.gson.annotations.Expose;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import iskallia.vault.config.Config;
import iskallia.vault.config.GearModelRollRaritiesConfig;
import iskallia.vault.gear.VaultGearRarity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vaultcrackerlib.api.registry.gear.CustomVaultGearRegistry;
import xyz.iwolfking.vaultcrackerlib.api.registry.generic.records.CustomVaultGearEntry;

import java.util.List;
import java.util.Map;

@Mixin(value = GearModelRollRaritiesConfig.class, remap = false)
public abstract class MixinGearModelRollRaritiesConfig extends Config {

    @Inject(method = "getRolls", at = @At("HEAD"), cancellable = true)
    private void getRollsHook(CallbackInfoReturnable<Map<VaultGearRarity, List<String>>> cir, @Local ItemStack stack) {
        for(CustomVaultGearEntry entry : CustomVaultGearRegistry.getCustomGearEntries()) {
            if(stack.getItem().equals(entry.registryItem())) {
                cir.setReturnValue(entry.modelRarityMap());
            }
        }
    }


}
