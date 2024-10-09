package xyz.iwolfking.vhapi.mixin.models;

import iskallia.vault.item.InfusedCatalystItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.iwolfking.vhapi.api.registry.CustomCatalystModelRegistry;

import java.util.function.Consumer;

@Mixin(value = InfusedCatalystItem.class, remap = false)
public abstract class MixinInfusedCatalystItem {

    @Inject(method = "loadModels", at = @At("TAIL"))
    public void loadModels(Consumer<ModelResourceLocation> consumer, CallbackInfo ci) {
        for(Integer i : CustomCatalystModelRegistry.getModelMap().keySet()) {
            consumer.accept(new ModelResourceLocation("the_vault:catalyst/%d#inventory".formatted(i)));
        }
    }
}
