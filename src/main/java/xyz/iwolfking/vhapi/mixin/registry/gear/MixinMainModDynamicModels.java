package xyz.iwolfking.vhapi.mixin.registry.gear;

import iskallia.vault.dynamodel.registry.DynamicModelRegistries;
import iskallia.vault.init.ModDynamicModels;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.iwolfking.vhapi.api.registry.VaultGearRegistry;
import xyz.iwolfking.vhapi.api.registry.gear.CustomVaultGearRegistryEntry;

@Mixin(value = ModDynamicModels.class, remap = false)
public class MixinMainModDynamicModels {
    @Shadow
    @Final
    public static DynamicModelRegistries REGISTRIES;

    @Inject(method = "initItemAssociations", at = @At("RETURN"))
    private static void initItemAssociations(CallbackInfo ci) {
        for(CustomVaultGearRegistryEntry entry : VaultGearRegistry.customGearRegistry.get().getValues()) {
            System.out.println(entry.getName());
            REGISTRIES.associate(entry.getRegistryItem(), entry.getDynamicModelRegistry());
        }
    }
}