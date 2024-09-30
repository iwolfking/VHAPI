package xyz.iwolfking.vaultcrackerlib.mixin.registry.gear;

import com.llamalad7.mixinextras.sugar.Local;
import iskallia.vault.config.gear.VaultGearWorkbenchConfig;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vaultcrackerlib.api.registry.gear.CustomVaultGearRegistry;
import xyz.iwolfking.vaultcrackerlib.api.registry.generic.records.CustomVaultGearEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mixin(VaultGearWorkbenchConfig.class)
public class MixinVaultGearWorkbenchConfig {
    @Inject(method = "registerConfigs", at = @At("TAIL"), remap = false)
    private static void extendRegisterConfigs(CallbackInfoReturnable<Map<Item, VaultGearWorkbenchConfig>> cir, @Local Map<Item, VaultGearWorkbenchConfig> gearConfig) {
        List<Item> newGearItems = new ArrayList<>();
        for(CustomVaultGearEntry entry : CustomVaultGearRegistry.getCustomGearEntries()) {
            newGearItems.add(entry.registryItem());
        }

        for (Item item : newGearItems) {
            gearConfig.put(item, (VaultGearWorkbenchConfig) (new VaultGearWorkbenchConfig(item).readConfig()));
        }
    }
}