package xyz.iwolfking.vhapi.mixin.registry.objective;

import iskallia.vault.core.vault.player.ClassicListenersLogic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vhapi.api.registry.VaultObjectiveRegistry;
import xyz.iwolfking.vhapi.api.registry.objective.CustomObjectiveRegistryEntry;

@Mixin(value = ClassicListenersLogic.class, remap = false)
public class MixinClassicListenersLogic  {

    @Inject(method = "getVaultObjective", at = @At("HEAD"), cancellable = true)
    private void addCustomObjectiveNames(String key, CallbackInfoReturnable<String> cir) {
        for(CustomObjectiveRegistryEntry entry : VaultObjectiveRegistry.customObjectiveRegistry.get().getValues()) {
            if(entry.getId().equals(key)) {
                cir.setReturnValue(entry.getName());
            }
        }
    }
}
