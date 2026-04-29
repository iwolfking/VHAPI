package xyz.iwolfking.vhapi.mixin.fixes;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import iskallia.vault.core.data.key.TemplateKey;
import iskallia.vault.item.OverworldInscriptionItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = OverworldInscriptionItem.class)
public class MixinOverworldInscriptionItem {
    @WrapOperation(method = "appendHoverText", at = @At(value = "INVOKE", target = "Liskallia/vault/core/data/key/TemplateKey;getName()Ljava/lang/String;"))
    private String nullCheck(TemplateKey instance, Operation<String> original) {
        if (instance == null){
            return null;
        }
        return original.call(instance);
    }
}
