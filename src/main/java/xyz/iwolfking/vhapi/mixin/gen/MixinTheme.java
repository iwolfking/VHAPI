package xyz.iwolfking.vhapi.mixin.gen;

import com.google.gson.Gson;
import iskallia.vault.core.vault.VaultRegistry;
import iskallia.vault.core.world.generator.theme.ClassicVaultTheme;
import iskallia.vault.core.world.generator.theme.Theme;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vhapi.api.LoaderRegistry;
import xyz.iwolfking.vhapi.api.loaders.Processors;
import xyz.iwolfking.vhapi.api.util.ResourceLocUtils;
import xyz.iwolfking.vhapi.mixin.accessors.ThemeAccessor;

@Mixin(value = Theme.class, remap = false)
public class MixinTheme {

    /**
     * @author iwolfking
     * @reason Redirect call to pull from Theme data files when applicable.
     */
    @Inject(method = "fromPath", at = @At("HEAD"), cancellable = true)
    private static void fromPath(String path, CallbackInfoReturnable<Theme> cir) {
        Theme theme = null;
        if(ResourceLocUtils.isResourceLocation(path)) {
            if(Processors.GenerationFileProcessors.GEN_THEME_LOADER.CUSTOM_CONFIGS.containsKey(new ResourceLocation(path))) {
                theme = Processors.GenerationFileProcessors.GEN_THEME_LOADER.CUSTOM_CONFIGS.get(new ResourceLocation(path));
            }
        }

        if(theme != null) {
            ((ThemeAccessor)theme).setPath(path);
            cir.setReturnValue(theme);
        }

    }
}
