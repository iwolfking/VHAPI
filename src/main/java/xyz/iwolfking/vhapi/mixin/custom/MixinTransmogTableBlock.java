package xyz.iwolfking.vhapi.mixin.custom;

import iskallia.vault.block.TransmogTableBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vhapi.config.VHAPIConfig;

import javax.annotation.Nullable;
import java.util.Collection;

/**
 * Mixin Class currently used to ignore the patreon check when checking in the transmog table
 */
@Mixin(value = TransmogTableBlock.class, remap = false)
public abstract class MixinTransmogTableBlock {
    @Shadow
    @Nullable
    private static Boolean checkModelAccess(Player player, ResourceLocation modelId) {
        return null;
    }

    @Inject(method = "canTransmogModel", at = @At("HEAD"), cancellable = true)
    private static void preventPatreonCheck(Player player, Collection<ResourceLocation> discoveredModelIds, ResourceLocation modelId, CallbackInfoReturnable<Boolean> cir) {
        if(VHAPIConfig.SERVER.allowPatreonUnlocks.get()) {
            Boolean allowed = checkModelAccess(player, modelId);
            cir.setReturnValue(allowed != null || discoveredModelIds.contains(modelId));
        }
    }

}
