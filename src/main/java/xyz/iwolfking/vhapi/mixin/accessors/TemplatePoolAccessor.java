package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.core.world.template.data.TemplatePool;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = TemplatePool.class, remap = false)
public interface TemplatePoolAccessor {
    @Accessor("path")
    public void setPath(String path);
}
