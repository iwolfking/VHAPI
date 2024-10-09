package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.core.world.processor.Palette;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = Palette.class, remap = false)
public interface PaletteAccessor {
    @Accessor("path")
    public void setPath(String path);
}
