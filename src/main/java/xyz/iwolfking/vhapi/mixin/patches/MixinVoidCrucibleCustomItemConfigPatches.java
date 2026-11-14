package xyz.iwolfking.vhapi.mixin.patches;

import com.google.gson.Gson;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import iskallia.vault.config.crucible.VoidCrucibleCustomItemConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import xyz.iwolfking.vhapi.api.patches.ConfigPatches;

import java.io.Reader;
import java.util.List;

@Mixin(value = VoidCrucibleCustomItemConfig.class, remap = false)
public abstract class MixinVoidCrucibleCustomItemConfigPatches {

    /** Applies the config patches for each void crucible item file the folder scan reads. */
    @WrapOperation(method = "readConfig", at = @At(value = "INVOKE", target = "Lcom/google/gson/Gson;fromJson(Ljava/io/Reader;Ljava/lang/Class;)Ljava/lang/Object;"))
    private Object vhapi$patchCrucibleItem(Gson gson, Reader reader, Class<?> type, Operation<Object> original, @Local String id) {
        String path = "void_crucible/" + id + ".json";
        List<ConfigPatches.Entry> patches = ConfigPatches.find(path);
        if (patches.isEmpty()) {
            return original.call(gson, reader, type);
        }
        return ConfigPatches.deserializePatched(path, patches, reader, patched -> original.call(gson, patched, type));
    }
}
