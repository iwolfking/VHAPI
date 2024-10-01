package xyz.iwolfking.vaultcrackerlib.mixin.configs.box;

import iskallia.vault.config.UnidentifiedTreasureKeyConfig;
import iskallia.vault.config.entry.vending.ProductEntry;
import iskallia.vault.util.data.WeightedList;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.Patchers;

import java.util.Random;

@Mixin(value = UnidentifiedTreasureKeyConfig.class, remap = false)
public class MixinUnidentifiedTreasureKeyConfig {
    @Shadow private WeightedList<ProductEntry> treasureKeys;


    @Inject(method = "getRandomKey", at = @At("HEAD"))
    public void getRandomKey(Random random, CallbackInfoReturnable<ItemStack> cir) {
        Patchers.UNIDENTIFIED_TREASURE_KEY_PATCHER.doPatches(treasureKeys);
    }
}
