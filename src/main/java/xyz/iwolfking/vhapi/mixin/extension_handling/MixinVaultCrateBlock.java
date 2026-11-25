package xyz.iwolfking.vhapi.mixin.extension_handling;

import iskallia.vault.block.VaultCrateBlock;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vhapi.api.extensions.general.VaultCrateTypeFactory;

@Mixin(value = VaultCrateBlock.class, remap = false)
public abstract class MixinVaultCrateBlock {

    @Inject(method = "getCrateBlock", at = @At("HEAD"), cancellable = true)
    private static void handleAdditionalCrates(VaultCrateBlock.Type type, CallbackInfoReturnable<Block> cir) {
        if (!VaultCrateTypeFactory.INSTANCE.isFactoryCreated(type)) {
            return;
        }

        VaultCrateTypeFactory.CratePair key = VaultCrateTypeFactory.INSTANCE.getCrateKey(type);
        if (key != null) {
            Block crateBlock = key.block();
            if (crateBlock != null) {
                cir.setReturnValue(crateBlock);
            }
        }
    }

}