package xyz.iwolfking.vaultcrackerlib.mixin.configs.workstations;

import iskallia.vault.config.AscensionForgeConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.Patchers;

import java.util.List;

@Mixin(value = AscensionForgeConfig.class, remap = false)
public class MixinAscensionForgeConfig {

    @Shadow protected List<AscensionForgeConfig.AscensionForgeListing> listings;

    @Inject(method = "getListings", at = @At("HEAD"))
    private void addCustomListings(CallbackInfoReturnable<List<AscensionForgeConfig.AscensionForgeListing>> cir) {
        Patchers.ASCENSION_FORGE_PATCHER.doPatches(listings);
    }
}
