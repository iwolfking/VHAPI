package xyz.iwolfking.vaultcrackerlib.mixin;

import iskallia.vault.init.ModConfigs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.research.ResearchGUIConfigPatcher;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.research.ResearchGroupGUIConfigPatcher;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.research.ResearchGroupPatcher;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.research.ResearchPatcher;

@Mixin(value = ModConfigs.class, remap = false)
public class MixinModConfigs {

    @Inject(method = "register", at = @At("TAIL"))
    private static void resetPatchedStateForPatchers(CallbackInfo ci) {
        ResearchPatcher.setPatched(false);
        ResearchGroupPatcher.setPatched(false);
        ResearchGUIConfigPatcher.setPatched(false);
        ResearchGroupGUIConfigPatcher.setPatched(false);
    }
}
