package xyz.iwolfking.vaultcrackerlib.mixin.registry.gear;


import iskallia.vault.config.gear.VaultGearWorkbenchConfig;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.Loaders;
import java.util.Optional;

@Mixin(value = VaultGearWorkbenchConfig.class, remap = false)
public class MixinVaultGearWorkbenchConfig {
    @Inject(method = "getConfig(Lnet/minecraft/world/item/Item;)Ljava/util/Optional;", at = @At("HEAD"), cancellable = true)
    private static void getConfig(Item item, CallbackInfoReturnable<Optional<VaultGearWorkbenchConfig>> cir) {
        if(Loaders.CUSTOM_VAULT_GEAR_WORKBENCH_LOADER.CUSTOM_CONFIGS.containsKey(item.getRegistryName()))  {
            cir.setReturnValue(Optional.ofNullable(Loaders.CUSTOM_VAULT_GEAR_WORKBENCH_LOADER.CUSTOM_CONFIGS.get(item.getRegistryName())));
        }
    }

}