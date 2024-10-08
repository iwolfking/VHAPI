package xyz.iwolfking.vaultcrackerlib.mixin.gen;

import iskallia.vault.core.world.processor.Palette;
import iskallia.vault.core.world.template.data.TemplatePool;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vaultcrackerlib.api.LoaderRegistry;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.PaletteAccessor;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.ThemeAccessor;

@Mixin(value = Palette.class, remap = false)
public class MixinPalette {
    @Inject(method = "fromPath", at = @At("HEAD"), cancellable = true)
    private static void fromPath(String path, CallbackInfoReturnable<Palette> cir) {
        Palette pool = null;
        if(path.startsWith("the_vault:")) {
            if(LoaderRegistry.GEN_PALETTE_LOADER.CUSTOM_CONFIGS.containsKey(new ResourceLocation(path))) {
                pool = LoaderRegistry.GEN_PALETTE_LOADER.CUSTOM_CONFIGS.get(new ResourceLocation(path));
            }
        }

        if(pool != null) {
            ((PaletteAccessor)pool).setPath(path);
            cir.setReturnValue(pool);
        }

    }
}
