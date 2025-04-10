package xyz.iwolfking.vhapi.mixin.gen;

import iskallia.vault.core.world.loot.LootTable;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vhapi.api.LoaderRegistry;
import xyz.iwolfking.vhapi.api.loaders.Processors;
import xyz.iwolfking.vhapi.api.util.ResourceLocUtils;
import xyz.iwolfking.vhapi.mixin.accessors.LootTableAccessor;

@Mixin(value = LootTable.class, remap = false)
public class MixinLootTable {
    @Inject(method = "fromPath", at = @At("HEAD"), cancellable = true)
    private static void fromPath(String path, CallbackInfoReturnable<LootTable> cir) {
        LootTable pool = null;

        if(ResourceLocUtils.isResourceLocation(path)) {
            if(Processors.GenerationFileProcessors.GEN_LOOT_TABLE_LOADER.CUSTOM_CONFIGS.containsKey(new ResourceLocation(path))) {
                pool = Processors.GenerationFileProcessors.GEN_LOOT_TABLE_LOADER.CUSTOM_CONFIGS.get(new ResourceLocation(path));
            }
        }

        if(pool != null) {
            ((LootTableAccessor)pool).setPath(path);
            cir.setReturnValue(pool);
        }

    }
}
