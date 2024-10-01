package xyz.iwolfking.vaultcrackerlib.mixin.configs.general;

import iskallia.vault.config.VaultLevelsConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.Patchers;

import java.util.List;

@Mixin(value = VaultLevelsConfig.class, remap = false)
public class MixinVaultLevelsConfig {
    @Shadow @Final private List<VaultLevelsConfig.VaultLevelMeta> levelMetas;

    @Inject(method = "getMaxLevel", at = @At("HEAD"), cancellable = true)
    private void replaceMaxLevel(CallbackInfoReturnable<Integer> cir) {
        if(Patchers.MAX_VAULT_LEVEL_PATCHER.getConstant() != null) {
            cir.setReturnValue(Patchers.MAX_VAULT_LEVEL_PATCHER.getConstant());
        }
    }

    @Inject(method = "getLevelMeta", at = @At("HEAD"))
    private void patchLevelMeta(int level, CallbackInfoReturnable<VaultLevelsConfig.VaultLevelMeta> cir) {
        Patchers.VAULT_LEVELS_PATCHER.doPatches(this.levelMetas);
    }
}
