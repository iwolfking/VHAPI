package xyz.iwolfking.vhapi.mixin.fixes;

import iskallia.vault.config.Config;
import iskallia.vault.config.VaultStashConfig;
import javax.annotation.Nullable;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.iwolfking.vhapi.mixin.accessors.VaultStashConfigAccessor;

@Mixin(value = VaultStashConfig.class, remap = false)
public abstract class MixinVaultStashConfig {

    @Inject(method = "onLoad", at = @At("HEAD"), cancellable = true)
    private void skipPartialConfigValidation(@Nullable Config oldConfigInstance, CallbackInfo ci) {
        VaultStashConfigAccessor accessor = (VaultStashConfigAccessor) this;
        VaultStashConfig.Looks looks = accessor.getLooks();
        Item facetedFocus = accessor.getFacetedFocus();

        boolean hasLooks = looks != null
                && looks.models != null && !looks.models.isEmpty()
                && looks.particles != null && !looks.particles.isEmpty();

        if (facetedFocus == null || !hasLooks) {
            ci.cancel();
        }
    }
}