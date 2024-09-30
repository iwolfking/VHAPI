package xyz.iwolfking.vaultcrackerlib.mixin.registry.objective;

import iskallia.vault.core.vault.player.ClassicListenersLogic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vaultcrackerlib.api.registry.generic.records.CustomVaultObjectiveEntry;
import xyz.iwolfking.vaultcrackerlib.api.registry.objective.CustomVaultObjectiveRegistry;

@Mixin(value = ClassicListenersLogic.class, remap = false)
public class MixinClassicListenersLogic  {

    @Inject(method = "getVaultObjective", at = @At("HEAD"), cancellable = true)
    private void addCustomObjectiveNames(String key, CallbackInfoReturnable<String> cir) {
        for(CustomVaultObjectiveEntry entry : CustomVaultObjectiveRegistry.getCustomVaultObjectiveEntries()) {
            if(entry.id().equals(key)) {
                cir.setReturnValue(entry.name());
            }
        }
    }
}
