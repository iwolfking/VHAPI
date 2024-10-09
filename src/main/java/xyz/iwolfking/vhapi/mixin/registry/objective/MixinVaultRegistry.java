package xyz.iwolfking.vhapi.mixin.registry.objective;

import iskallia.vault.core.data.key.registry.SupplierRegistry;
import iskallia.vault.core.vault.VaultRegistry;
import iskallia.vault.core.vault.objective.Objective;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = VaultRegistry.class, remap = false)
public abstract class MixinVaultRegistry {
    @Shadow
    @Final
    public static SupplierRegistry<Objective> OBJECTIVE;


    static {

    }



}
