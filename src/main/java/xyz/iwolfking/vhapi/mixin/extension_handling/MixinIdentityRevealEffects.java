package xyz.iwolfking.vhapi.mixin.extension_handling;

import iskallia.vault.client.gui.identify.IdentifyRevealEffects;
import iskallia.vault.gear.VaultGearRarity;
import net.minecraft.sounds.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = IdentifyRevealEffects.class, remap = false)
public class MixinIdentityRevealEffects {
    @Inject(method = "soundFor", at = @At(value = "HEAD"), cancellable = true)
    private static void extendSoundFor(VaultGearRarity rarity, CallbackInfoReturnable<SoundEvent> cir) {

    }

}
