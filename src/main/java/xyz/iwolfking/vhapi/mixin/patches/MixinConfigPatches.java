package xyz.iwolfking.vhapi.mixin.patches;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import iskallia.vault.VaultMod;
import iskallia.vault.config.Config;
import iskallia.vault.init.ModConfigs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vhapi.VHAPI;
import xyz.iwolfking.vhapi.api.patches.ConfigPatches;
import xyz.iwolfking.vhapi.mixin.accessors.ConfigAccessor;

import java.io.File;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

@Mixin(value = Config.class, remap = false)
public abstract class MixinConfigPatches {

    /**
     * Applies the config patches for this file, when there are any, to the disk JSON before the base mod
     * deserializes it. A patched tree that fails to deserialize falls back to the unpatched file, so the base mod
     * never replaces the disk file with its Java defaults because of a patch.
     */
    @WrapOperation(method = "readConfig", at = @At(value = "INVOKE", target = "Lcom/google/gson/Gson;fromJson(Ljava/io/Reader;Ljava/lang/reflect/Type;)Ljava/lang/Object;"))
    private Object vhapi$patchConfig(Gson gson, Reader reader, Type type, Operation<Object> original) {
        String path = ConfigPatches.relativePath(((ConfigAccessor) this).vhapi$getConfigFile());
        List<ConfigPatches.Entry> patches = ConfigPatches.find(path);
        if (patches.isEmpty()) {
            return original.call(gson, reader, type);
        }
        return ConfigPatches.deserializePatched(path, patches, reader, patched -> original.call(gson, patched, type));
    }

    /**
     * Loads a config from the patch that creates it. The patch wins over any file already on disk (a stale copy or
     * one the base mod wrote with its defaults); a create that fails falls through to the base mod's own read.
     */
    @Inject(method = "readConfig", at = @At("HEAD"), cancellable = true)
    private void vhapi$createConfig(CallbackInfoReturnable<Config> cir) {
        Config self = (Config) (Object) this;
        File file = ((ConfigAccessor) self).vhapi$getConfigFile();
        String path = ConfigPatches.relativePath(file);
        if (path == null || ConfigPatches.find(path).isEmpty()) {
            return;
        }
        try {
            JsonElement created = ConfigPatches.created(path);
            if (created == null) {
                return;
            }
            if (file.exists()) {
                VHAPI.LOGGER.warn("Config {} is created by a config patch; ignoring the file on disk", path);
            }
            VaultMod.LOGGER.info("Reading config: " + self.getName() + " (created by a config patch)");
            Config config = self.getGson().fromJson(created, self.getClass());
            ConfigAccessor accessor = (ConfigAccessor) config;
            accessor.vhapi$onLoad(self);
            if (!accessor.vhapi$isValid()) {
                VaultMod.LOGGER.error("Invalid config {}, using defaults", self);
                ModConfigs.INVALID_CONFIGS.add(file.getName() + " - There was an invalid setting in this config.");
                accessor.vhapi$reset();
            }
            ModConfigs.CONFIGS.add(config);
            cir.setReturnValue(config);
        } catch (RuntimeException e) {
            VHAPI.LOGGER.error("Config patch for {} could not create the config; falling back to the base mod's read", path, e);
        }
    }
}
