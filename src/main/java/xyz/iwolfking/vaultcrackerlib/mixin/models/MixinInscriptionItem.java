package xyz.iwolfking.vaultcrackerlib.mixin.models;

import iskallia.vault.item.InscriptionItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.iwolfking.vaultcrackerlib.api.registry.models.CustomInscriptionModelRegistry;

import java.util.function.Consumer;

@Mixin(value = InscriptionItem.class, remap = false)
public class MixinInscriptionItem {
    /**
     * @author iwolfking
     * @reason To allow new Inscription Models to be registered.
     */
    @Inject(method = "loadModels", at = @At("TAIL"))
    private void loadModels(Consumer<ModelResourceLocation> consumer, CallbackInfo ci) {
        for(Integer i : CustomInscriptionModelRegistry.getModelMap().keySet()) {
            consumer.accept(new ModelResourceLocation("the_vault:inscription/%d#inventory".formatted(i)));
        }
    }
}

