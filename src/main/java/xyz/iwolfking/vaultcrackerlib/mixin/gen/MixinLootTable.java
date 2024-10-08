package xyz.iwolfking.vaultcrackerlib.mixin.gen;

import iskallia.vault.core.world.loot.LootTable;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vaultcrackerlib.api.LoaderRegistry;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.LootTableAccessor;

@Mixin(value = LootTable.class, remap = false)
public class MixinLootTable {
    @Inject(method = "fromPath", at = @At("HEAD"), cancellable = true)
    private static void fromPath(String path, CallbackInfoReturnable<LootTable> cir) {
        LootTable pool = null;
        if(path.startsWith("vhapi:")) {
            if(LoaderRegistry.GEN_LOOT_TABLE_LOADER.CUSTOM_CONFIGS.containsKey(new ResourceLocation(path))) {
                pool = LoaderRegistry.GEN_LOOT_TABLE_LOADER.CUSTOM_CONFIGS.get(new ResourceLocation(path));
            }
        }

        if(pool != null) {
            ((LootTableAccessor)pool).setPath(path);
            cir.setReturnValue(pool);
        }

    }
}
