package xyz.iwolfking.vaultcrackerlib.mixin.registry.gear;

import com.llamalad7.mixinextras.sugar.Local;
import iskallia.vault.config.gear.VaultGearWorkbenchConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.Loaders;
import xyz.iwolfking.vaultcrackerlib.api.registry.gear.CustomVaultGearRegistry;
import xyz.iwolfking.vaultcrackerlib.api.registry.generic.records.CustomVaultGearEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mixin(value = VaultGearWorkbenchConfig.class, remap = false)
public class MixinVaultGearWorkbenchConfig {
    @Inject(method = "registerConfigs", at = @At("TAIL"), remap = false)
    private static void extendRegisterConfigs(CallbackInfoReturnable<Map<Item, VaultGearWorkbenchConfig>> cir, @Local Map<Item, VaultGearWorkbenchConfig> gearConfig) {
        List<Item> newGearItems = new ArrayList<>();
        for(CustomVaultGearEntry entry : CustomVaultGearRegistry.getCustomGearEntries()) {
            newGearItems.add(entry.registryItem());
        }


        for (Item item : newGearItems) {
            gearConfig.put(item, Loaders.CUSTOM_VAULT_GEAR_WORKBENCH_LOADER.CUSTOM_CONFIGS.get(new ResourceLocation("the_vault", item.getRegistryName().getPath())));
        }
    }

    @Inject(method = "getConfig(Lnet/minecraft/world/item/Item;)Ljava/util/Optional;", at = @At("HEAD"), cancellable = true)
    private static void getConfig(Item item, CallbackInfoReturnable<Optional<VaultGearWorkbenchConfig>> cir) {
        if(Loaders.CUSTOM_VAULT_GEAR_WORKBENCH_LOADER.CUSTOM_CONFIGS.containsKey(item.getRegistryName()))  {
            cir.setReturnValue(Optional.ofNullable(Loaders.CUSTOM_VAULT_GEAR_WORKBENCH_LOADER.CUSTOM_CONFIGS.get(item.getRegistryName())));
        }
    }

}