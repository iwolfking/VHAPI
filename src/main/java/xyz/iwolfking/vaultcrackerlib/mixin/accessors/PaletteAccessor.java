package xyz.iwolfking.vaultcrackerlib.mixin.accessors;

import iskallia.vault.core.world.processor.Palette;
import iskallia.vault.core.world.template.data.TemplatePool;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = Palette.class, remap = false)
public interface PaletteAccessor {
    @Accessor("path")
    public void setPath(String path);
}
