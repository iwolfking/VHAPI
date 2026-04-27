package xyz.iwolfking.vhapi.mixin;

import iskallia.vault.init.ModConfigs;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.events.VaultGenConfigEvent;


@Mixin(value = ModConfigs.class, remap = false)
public class MixinModConfigs {

    @Inject(method = "register", at = @At("HEAD"))
    private static void invokeBeginConfigLoadEvent(CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new VaultConfigEvent.Begin());
    }

    @Inject(method = "register", at = @At("TAIL"))
    private static void resetPatchedStateForPatchers(CallbackInfo ci) {
        //Add built-in VHAPI configs
        xyz.iwolfking.vhapi.init.ModConfigs.register();

        MinecraftForge.EVENT_BUS.post(new VaultConfigEvent.End());
    }


    @Inject(method = "registerGen", at = @At("HEAD"))
    private static void invokeBeginGenConfigLoadEvent(CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new VaultGenConfigEvent.Begin());
    }

    @Inject(method = "registerGen", at = @At(value = "INVOKE", target = "Liskallia/vault/core/data/key/registry/KeyRegistry;getKeys()Ljava/util/List;", ordinal = 0))
    private static void invokeAfterRegistriesGenConfigLoadEvent(CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new VaultGenConfigEvent.RegistriesBuilt());
    }

    @Inject(method = "registerGen", at = @At(value = "TAIL"))
    private static void invokeAfterGenConfigLoadEvent(CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new VaultGenConfigEvent.End());
    }

}
