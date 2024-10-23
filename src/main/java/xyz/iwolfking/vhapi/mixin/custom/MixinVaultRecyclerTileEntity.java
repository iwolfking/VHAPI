package xyz.iwolfking.vhapi.mixin.custom;

import iskallia.vault.block.entity.VaultRecyclerTileEntity;
import iskallia.vault.config.VaultRecyclerConfig;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vhapi.api.data.api.CustomRecyclerOutputs;

import java.util.UUID;

@Mixin(value = VaultRecyclerTileEntity.class, remap = false)
public abstract class MixinVaultRecyclerTileEntity {
    @Shadow @Final private VaultRecyclerTileEntity.RecyclerInventory inventory;
    @Shadow private UUID gearIdProcessing;

    @Inject(method = "isValidInput", at = @At("HEAD"), cancellable = true)
    private void isValidCustomInput(ItemStack input, CallbackInfoReturnable<Boolean> cir) {
        if(CustomRecyclerOutputs.CUSTOM_OUTPUTS.containsKey(input.getItem().getRegistryName())) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "getResultPercentage", at = @At("HEAD"), cancellable = true)
    private void getCustomResultPercentage(ItemStack input, CallbackInfoReturnable<Float> cir) {
        if(CustomRecyclerOutputs.CUSTOM_OUTPUTS.containsKey(input.getItem().getRegistryName())) {
            cir.setReturnValue(1.0F);
        }
    }

    @Inject(method = "getRecipeOutput", at = @At("HEAD"), cancellable = true)
    private void getCustomRecipeOutput(CallbackInfoReturnable<VaultRecyclerConfig.RecyclerOutput> cir) {
        ItemStack input= this.inventory.getItem(0);
        if(CustomRecyclerOutputs.CUSTOM_OUTPUTS.containsKey(input.getItem().getRegistryName())) {
            cir.setReturnValue(CustomRecyclerOutputs.CUSTOM_OUTPUTS.get(input.getItem().getRegistryName()));
        }
    }

    @Inject(method = "triggerItemChange", at = @At("HEAD"), cancellable = true)
    private void triggerCustomItemChange(CallbackInfo ci) {
        ItemStack input = this.inventory.getItem(0);
        if(CustomRecyclerOutputs.CUSTOM_OUTPUTS.containsKey(input.getItem().getRegistryName())) {
            UUID itemId = UUID.randomUUID();
            if ((this.gearIdProcessing == null || !this.gearIdProcessing.equals(itemId)) && this.canCraft()) {
                this.startProcess((Level)null, itemId);
                ci.cancel();
            }
        }
    }

    @Shadow protected abstract boolean canCraft();

    @Shadow protected abstract void startProcess(@Nullable Level world, UUID id);
}
