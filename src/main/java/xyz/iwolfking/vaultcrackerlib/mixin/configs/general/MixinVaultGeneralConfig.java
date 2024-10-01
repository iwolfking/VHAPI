package xyz.iwolfking.vaultcrackerlib.mixin.configs.general;

import iskallia.vault.config.VaultGeneralConfig;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.Patchers;

import java.util.List;


@Mixin(value = VaultGeneralConfig.class, remap = false)
public class MixinVaultGeneralConfig {

    @Shadow private List<String> ITEM_BLACKLIST;

    @Shadow private List<String> BLOCK_BLACKLIST;

    @Inject(method = "isBlacklisted(Lnet/minecraft/world/item/ItemStack;)Z", at = @At("HEAD"))
    private void patchItemBlacklist(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        Patchers.ITEM_BLACKLIST_PATCHER.doPatches(ITEM_BLACKLIST);
    }

    @Inject(method = "isBlacklisted(Lnet/minecraft/world/level/block/state/BlockState;)Z", at = @At("HEAD"))
    private void patchItemBlacklist(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        Patchers.BLOCK_BLACKLIST_PATCHER.doPatches(BLOCK_BLACKLIST);
    }

    @Inject(method = "isBlacklisted(Lnet/minecraft/world/item/Item;)Z", at = @At("HEAD"))
    private void patchItemBlacklist(Item item, CallbackInfoReturnable<Boolean> cir) {
        Patchers.ITEM_BLACKLIST_PATCHER.doPatches(ITEM_BLACKLIST);
    }

    @Inject(method = "isBlacklisted(Lnet/minecraft/world/level/block/Block;)Z", at = @At("HEAD"))
    private void patchItemBlacklist(Block block, CallbackInfoReturnable<Boolean> cir) {
        Patchers.BLOCK_BLACKLIST_PATCHER.doPatches(BLOCK_BLACKLIST);
    }


}
