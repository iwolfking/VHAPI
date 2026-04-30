package xyz.iwolfking.vhapi.mixin.fixes;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import iskallia.vault.client.render.SchematicStructurePreview;
import iskallia.vault.core.world.template.Template;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import xyz.iwolfking.vhapi.VHAPI;

@Mixin(value = SchematicStructurePreview.class, remap = false)
public class MixinSchematicStructurePreview {
    @WrapOperation(method = "load", at = @At(value = "INVOKE", target = "Liskallia/vault/client/render/SchematicStructurePreview;fromTemplate(Liskallia/vault/core/world/template/Template;)Liskallia/vault/client/render/SchematicStructurePreview;"))
    private static SchematicStructurePreview catchException(Template template, Operation<SchematicStructurePreview> original){
        try {
            // will throw if template exists in the config but structure file doesn't
            return original.call(template);
        } catch (Exception e) {
            VHAPI.LOGGER.error(e.getMessage());
        }
        return null;
    }
}
