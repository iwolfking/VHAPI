package xyz.iwolfking.vhapi.mixin.registry.gear;

import iskallia.vault.config.gear.VaultGearTierConfig;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vhapi.api.loaders.Processors;
import xyz.iwolfking.vhapi.api.loaders.gear.CustomVaultGearLoader;
import xyz.iwolfking.vhapi.api.loaders.lib.depr.VaultConfigDataLoader;
import xyz.iwolfking.vhapi.api.LoaderRegistry;
import xyz.iwolfking.vhapi.api.util.ResourceLocUtils;

import java.util.Optional;

@Mixin(value = VaultGearTierConfig.class, remap = false)
public abstract class MixinVaultGearToolTier {
    @Inject(method = "getConfig(Lnet/minecraft/resources/ResourceLocation;)Ljava/util/Optional;", at = @At("HEAD"), cancellable = true)
    private static void getCustomConfig(ResourceLocation key, CallbackInfoReturnable<Optional<VaultGearTierConfig>> cir) {
        CustomVaultGearLoader loader = Processors.VaultGearConfigProcessors.CUSTOM_VAULT_GEAR_LOADER;
            if(loader.CUSTOM_CONFIGS.containsKey(ResourceLocUtils.swapNamespace(key, "vhapi"))) {
                VaultGearTierConfig tierConfig = (VaultGearTierConfig) loader.CUSTOM_CONFIGS.get(ResourceLocUtils.swapNamespace(key, "vhapi"));
                cir.setReturnValue(Optional.ofNullable(tierConfig));
            }

    }
}

