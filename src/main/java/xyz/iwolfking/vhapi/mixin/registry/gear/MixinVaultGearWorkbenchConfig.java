package xyz.iwolfking.vhapi.mixin.registry.gear;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import iskallia.vault.config.gear.VaultGearWorkbenchConfig;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vhapi.api.registry.VaultGearRegistry;
import xyz.iwolfking.vhapi.api.registry.gear.CustomVaultGearRegistryEntry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Mixin(value = VaultGearWorkbenchConfig.class, remap = false)
public class MixinVaultGearWorkbenchConfig {
    @Inject(method = "registerConfigs", at = @At("TAIL"), remap = false)
    private static void extendRegisterConfigs(CallbackInfoReturnable<Map<Item, VaultGearWorkbenchConfig>> cir, @Local LocalRef<Map<Item, VaultGearWorkbenchConfig>> gearConfig) {
        List<Item> newGearItems = new ArrayList<>();
        for(CustomVaultGearRegistryEntry entry : VaultGearRegistry.customGearRegistry.get().getValues()) {
            newGearItems.add(entry.getRegistryItem());
        }


        for (Item item : newGearItems) {
            gearConfig.get().put(item, new VaultGearWorkbenchConfig(item).readConfig());
        }
    }
}
