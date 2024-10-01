package xyz.iwolfking.vaultcrackerlib.mixin.configs.augment;

import iskallia.vault.config.AugmentConfig;
import iskallia.vault.core.random.RandomSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.Patchers;

import java.util.Map;
import java.util.Optional;

@Mixin(value = AugmentConfig.class, remap = false)
public class MixinAugmentConfig {

    @Shadow private Map<ResourceLocation, Map<String, Integer>> drops;

    @Inject(method = "generate", at = @At("HEAD"))
    private void patchAugments(ResourceLocation id, RandomSource random, CallbackInfoReturnable<Optional<ItemStack>> cir) {
        Patchers.AUGMENT_PATCHER.doPatches(this.drops);
    }
}
