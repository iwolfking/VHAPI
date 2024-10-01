package xyz.iwolfking.vaultcrackerlib.mixin.configs.misc;

import iskallia.vault.config.TooltipConfig;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.Patchers;

import java.util.List;
import java.util.Optional;

@Mixin(value = TooltipConfig.class, remap = false)
public class MixinTooltipConfig {
    @Shadow @Final private List<TooltipConfig.TooltipEntry> tooltips;

    @Inject(method = "getTooltipString", at = @At("HEAD"))
    public void getTooltipString(Item item, CallbackInfoReturnable<Optional<String>> cir) {
        Patchers.TOOLTIP_PATCHER.doPatches(tooltips);
    }
}
