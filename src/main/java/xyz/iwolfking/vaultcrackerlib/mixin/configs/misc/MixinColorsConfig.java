package xyz.iwolfking.vaultcrackerlib.mixin.configs.misc;

import iskallia.vault.config.ColorsConfig;
import net.minecraft.network.chat.TextColor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.Patchers;

@Mixin(targets = "iskallia.vault.config.ColorsConfig$Colors", remap = false)
public class MixinColorsConfig {

    @Inject(method = "get", at = @At("HEAD"), cancellable = true)
    public void get(String key, CallbackInfoReturnable<TextColor> cir) {
        if(Patchers.COLORS_PATCHER.getAdditions().containsKey(key)) {
            cir.setReturnValue(Patchers.COLORS_PATCHER.getAdditions().get(key));
        }
    }
}
