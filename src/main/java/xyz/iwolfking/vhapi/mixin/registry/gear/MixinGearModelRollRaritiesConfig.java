package xyz.iwolfking.vhapi.mixin.registry.gear;


import com.llamalad7.mixinextras.sugar.Local;
import iskallia.vault.config.Config;
import iskallia.vault.config.GearModelRollRaritiesConfig;
import iskallia.vault.gear.VaultGearRarity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vhapi.api.data.CustomGearModelRolls;


import java.util.List;
import java.util.Map;

@Mixin(value = GearModelRollRaritiesConfig.class, remap = false)
public abstract class MixinGearModelRollRaritiesConfig extends Config {

    @Inject(method = "getRolls", at = @At("HEAD"), cancellable = true)
    private void getRollsHook(CallbackInfoReturnable<Map<VaultGearRarity, List<String>>> cir, @Local ItemStack stack) {
        if(CustomGearModelRolls.CUSTOM_MODEL_ROLLS_MAP.containsKey(stack.getItem().getRegistryName())) {
            cir.setReturnValue(CustomGearModelRolls.CUSTOM_MODEL_ROLLS_MAP.get(stack.getItem().getRegistryName()));
        }
    }


}
