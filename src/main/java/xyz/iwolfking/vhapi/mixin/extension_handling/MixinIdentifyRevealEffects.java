package xyz.iwolfking.vhapi.mixin.extension_handling;

import iskallia.vault.client.gui.identify.IdentifyRevealEffects;
import iskallia.vault.gear.VaultGearRarity;
import net.minecraft.sounds.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vhapi.api.extensions.gear.VaultGearRarityFactory;

@Mixin(value = IdentifyRevealEffects.class, remap = false)
public class MixinIdentifyRevealEffects {

    @Inject(method = "soundFor", at = @At("HEAD"), cancellable = true)
    private static void vhapi$customRaritySound(VaultGearRarity rarity, CallbackInfoReturnable<SoundEvent> cir) {
        SoundEvent sound = VaultGearRarityFactory.INSTANCE.getIdentifySound(rarity);
        if (sound != null) {
            cir.setReturnValue(sound);
        }
    }

    @Inject(method = "pixelsFor", at = @At("HEAD"), cancellable = true)
    private static void vhapi$customRarityPixels(VaultGearRarity rarity, CallbackInfoReturnable<Integer> cir) {
        Integer pixels = VaultGearRarityFactory.INSTANCE.getIdentifyPixels(rarity);
        if (pixels != null) {
            cir.setReturnValue(pixels);
        }
    }
}