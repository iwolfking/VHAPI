package xyz.iwolfking.vaultcrackerlib.mixin.registry.gear;

import iskallia.vault.config.gear.VaultGearTierConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vaultcrackerlib.api.loaders.CustomVaultGearLoader;

import java.util.Optional;

@Mixin(value = VaultGearTierConfig.class, remap = false)
public abstract class MixinVaultGearToolTier {





    /**
     * @author iwolfking
     * @reason Testing...
     */
    @Inject(method = "getConfig(Lnet/minecraft/resources/ResourceLocation;)Ljava/util/Optional;", at = @At("HEAD"), cancellable = true)
    static void getCustomConfig(ResourceLocation key, CallbackInfoReturnable<Optional<VaultGearTierConfig>> cir) {
        if(ModConfigs.VAULT_GEAR_CONFIG.get(key) == null) {
            cir.setReturnValue(Optional.ofNullable(CustomVaultGearLoader.CUSTOM_GEAR_CONFIGS.get(key)));
        }
    }
}

