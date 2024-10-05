package xyz.iwolfking.vaultcrackerlib.mixin.registry.gear;


import iskallia.vault.config.gear.VaultGearWorkbenchConfig;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear.CustomVaultGearWorkbenchLoader;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vaultcrackerlib.api.LoaderRegistry;
import java.util.Optional;

@Mixin(value = VaultGearWorkbenchConfig.class, remap = false)
public class MixinVaultGearWorkbenchConfig {
    @Inject(method = "getConfig(Lnet/minecraft/world/item/Item;)Ljava/util/Optional;", at = @At("HEAD"), cancellable = true)
    private static void getConfig(Item item, CallbackInfoReturnable<Optional<VaultGearWorkbenchConfig>> cir) {
        for(VaultConfigDataLoader<?> loader : LoaderRegistry.getLoadersByType(CustomVaultGearWorkbenchLoader.class)) {
            if(loader.CUSTOM_CONFIGS.containsKey(item.getRegistryName())) {
                VaultGearWorkbenchConfig workbenchConfig = (VaultGearWorkbenchConfig)loader.CUSTOM_CONFIGS.get(item.getRegistryName());
                cir.setReturnValue(Optional.ofNullable(workbenchConfig));
            }
        }

    }

}