package xyz.iwolfking.vhapi.mixin.custom;

import iskallia.vault.util.calc.AttributeLimitHelper;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vhapi.init.ModConfigs;

@Mixin(value = AttributeLimitHelper.class, remap = false)
public class MixinLuckyHitHelper {
    @Inject(method = "getLuckyHitChanceLimit", at = @At("HEAD"), cancellable = true)
    private static void getModifiedLuckyHitLimit(LivingEntity entity, CallbackInfoReturnable<Float> cir) {
        if(ModConfigs.ATTRIBUTE_CAP_CONFIG.enableOverrides) {
            cir.setReturnValue(ModConfigs.ATTRIBUTE_CAP_CONFIG.luckyHitCap);
        }
    }

    @Inject(method = "getAreaOfEffectLimit", at = @At("HEAD"), cancellable = true)
    private static void getModifiedAOELimit(LivingEntity entity, CallbackInfoReturnable<Float> cir) {
        if(ModConfigs.ATTRIBUTE_CAP_CONFIG.enableOverrides) {
            cir.setReturnValue(ModConfigs.ATTRIBUTE_CAP_CONFIG.aoeCap);
        }
    }

}
