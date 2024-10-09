package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.core.world.generator.theme.Theme;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = Theme.class, remap = false)
public interface ThemeAccessor {
    @Accessor("path")
    public void setPath(String path);
}
