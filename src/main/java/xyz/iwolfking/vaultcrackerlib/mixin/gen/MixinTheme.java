package xyz.iwolfking.vaultcrackerlib.mixin.gen;

import com.google.gson.Gson;
import iskallia.vault.core.vault.Vault;
import iskallia.vault.core.world.generator.theme.Theme;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vaultcrackerlib.api.LoaderRegistry;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.ThemeAccessor;

import java.io.FileNotFoundException;
import java.io.FileReader;

@Mixin(value = Theme.class, remap = false)
public class MixinTheme {
    @Shadow @Final private static Gson GSON;

    /**
     * @author iwolfking
     * @reason Redirect call to pull from Theme data files when applicable.
     */
    @Inject(method = "fromPath", at = @At("HEAD"), cancellable = true)
    private static void fromPath(String path, CallbackInfoReturnable<Theme> cir) {
        Theme theme = null;
        if(path.startsWith("vhapi:")) {
            if(LoaderRegistry.GEN_THEME_LOADER.CUSTOM_CONFIGS.containsKey(new ResourceLocation(path))) {
                theme = LoaderRegistry.GEN_THEME_LOADER.CUSTOM_CONFIGS.get(new ResourceLocation(path));
            }
        }

        if(theme != null) {
            ((ThemeAccessor)theme).setPath(path);
            cir.setReturnValue(theme);
        }

    }
}
