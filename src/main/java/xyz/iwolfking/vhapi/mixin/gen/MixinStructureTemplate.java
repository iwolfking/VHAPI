package xyz.iwolfking.vhapi.mixin.gen;

import iskallia.vault.core.world.template.StructureTemplate;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vhapi.api.util.ResourceLocUtils;

import java.io.IOException;

@Mixin(value = StructureTemplate.class, remap = false)
public abstract class MixinStructureTemplate {
    @Shadow private String path;

    @Shadow public abstract void deserializeNBT(CompoundTag nbt);

    @Inject(method = "lambda$initializeIfNot$4", at = @At("HEAD"), cancellable = true)
    private void structureFromRL(CallbackInfoReturnable<Void> cir){
        if (ResourceLocUtils.isResourceLocation(this.path)) {
            var manager = ServerLifecycleHooks.getCurrentServer().getResourceManager();
            var rl = ResourceLocation.parse(this.path);
            if (!manager.hasResource(rl)) {
                throw new RuntimeException("[VHAPI] Structure template doesn't exist: " + this.path);
            }
            try (var is =  manager.getResource(rl).getInputStream()) {
                this.deserializeNBT(NbtIo.readCompressed(is));
                cir.setReturnValue(null);
             } catch (IOException e) {
                throw new RuntimeException("[VHAPI] Failed to load template from " + this.path, e);
            }

        }
    }
}
