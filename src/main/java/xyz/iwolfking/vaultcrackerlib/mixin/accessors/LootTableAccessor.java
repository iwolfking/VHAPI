package xyz.iwolfking.vaultcrackerlib.mixin.accessors;

import iskallia.vault.core.world.loot.LootTable;
import iskallia.vault.core.world.processor.Palette;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = LootTable.class, remap = false)
public interface LootTableAccessor {
    @Accessor("path")
    public void setPath(String path);
}
