package xyz.iwolfking.vaultcrackerlib.mixin.configs.workstations;

import iskallia.vault.config.VaultDiffuserConfig;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.Patchers;

import java.util.Map;

@Mixin(value = VaultDiffuserConfig.class, remap = false)
public class MixinVaultDiffuserConfig {
    @Shadow private Map<ResourceLocation, Integer> diffuserOutputMap;

    @Inject(method = "getDiffuserOutputMap", at = @At("HEAD"))
    private void patchVaultDiffuserMap(CallbackInfoReturnable<Map<ResourceLocation, Integer>> cir) {
        Patchers.VAULT_DIFFUSER_PATCHER.doPatches(diffuserOutputMap);
    }
}
