package xyz.iwolfking.vhapi.mixin.patches;

import com.google.gson.Gson;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import iskallia.vault.core.world.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import xyz.iwolfking.vhapi.api.patches.ConfigPatches;

import java.io.Reader;
import java.util.List;

@Mixin(value = LootTable.class, remap = false)
public abstract class MixinLootTablePatches {

    /** Applies the config patches for a generation loot table, when there are any, to its disk JSON before it is read. */
    @WrapOperation(method = "fromPath", at = @At(value = "INVOKE", target = "Lcom/google/gson/Gson;fromJson(Ljava/io/Reader;Ljava/lang/Class;)Ljava/lang/Object;"))
    private static Object vhapi$patchLootTable(Gson gson, Reader reader, Class<?> type, Operation<Object> original, @Local(argsOnly = true) String filePath) {
        String path = ConfigPatches.relativePath(filePath);
        List<ConfigPatches.Entry> patches = ConfigPatches.find(path);
        if (patches.isEmpty()) {
            return original.call(gson, reader, type);
        }
        return ConfigPatches.deserializePatched(path, patches, reader, patched -> original.call(gson, patched, type));
    }
}
