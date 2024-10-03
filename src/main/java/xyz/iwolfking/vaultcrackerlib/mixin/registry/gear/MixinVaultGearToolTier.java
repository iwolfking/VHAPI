package xyz.iwolfking.vaultcrackerlib.mixin.registry.gear;

import iskallia.vault.config.gear.VaultGearTierConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.world.WorldEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.CustomVaultGearLoader;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.Loaders;

import java.util.Optional;

@Mixin(value = VaultGearTierConfig.class, remap = false)
public abstract class MixinVaultGearToolTier {





    /**
     * @author iwolfking
     * @reason Testing...
     */
    @Inject(method = "getConfig(Lnet/minecraft/resources/ResourceLocation;)Ljava/util/Optional;", at = @At("HEAD"), cancellable = true)
    private static void getCustomConfig(ResourceLocation key, CallbackInfoReturnable<Optional<VaultGearTierConfig>> cir) {
        if(Loaders.CUSTOM_VAULT_GEAR_LOADER.CUSTOM_CONFIGS.containsKey(key)) {
            cir.setReturnValue(Optional.ofNullable(Loaders.CUSTOM_VAULT_GEAR_LOADER.CUSTOM_CONFIGS.get(key)));
        }
    }
}

