package xyz.iwolfking.vaultcrackerlib.mixin.registry.objective;

import iskallia.vault.core.data.key.registry.SupplierRegistry;
import iskallia.vault.core.vault.VaultRegistry;
import iskallia.vault.core.vault.objective.Objective;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import xyz.iwolfking.vaultcrackerlib.api.registry.generic.records.CustomVaultObjectiveEntry;
import xyz.iwolfking.vaultcrackerlib.api.registry.objective.CustomVaultObjectiveRegistry;

@Mixin(value = VaultRegistry.class, remap = false)
public abstract class MixinVaultRegistry {
    @Shadow
    @Final
    public static SupplierRegistry<Objective> OBJECTIVE;


    static {

    }



}
