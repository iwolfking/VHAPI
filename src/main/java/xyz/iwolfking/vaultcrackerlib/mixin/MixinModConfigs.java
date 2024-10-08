package xyz.iwolfking.vaultcrackerlib.mixin;

import iskallia.vault.init.ModConfigs;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;


@Mixin(value = ModConfigs.class, remap = false)
public class MixinModConfigs {

    @Inject(method = "register", at = @At("HEAD"))
    private static void invokeBeginConfigLoadEvent(CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new VaultConfigEvent.Begin(VaultConfigEvent.Type.NORMAL));
    }

    @Inject(method = "register", at = @At("TAIL"))
    private static void resetPatchedStateForPatchers(CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new VaultConfigEvent.End(VaultConfigEvent.Type.NORMAL));
    }


    @Inject(method = "register", at = @At("HEAD"))
    private static void invokeBeginGenConfigLoadEvent(CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new VaultConfigEvent.Begin(VaultConfigEvent.Type.GEN));
    }

    @Inject(method = "registerGen", at = @At("TAIL"))
    private static void invokeAfterGenConfigLoadEvent(CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new VaultConfigEvent.End(VaultConfigEvent.Type.GEN));
    }

}
