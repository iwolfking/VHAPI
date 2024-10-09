package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.core.data.key.TemplatePoolKey;
import iskallia.vault.core.world.generator.theme.ClassicVaultTheme;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = ClassicVaultTheme.class, remap = false)
public interface ClassicVaultThemeAccessor {
    @Accessor("starts")
    public void setStarts(TemplatePoolKey key);

    @Accessor("rooms")
    public void setRooms(TemplatePoolKey key);

    @Accessor("tunnels")
    public void setTunnels(TemplatePoolKey key);
}
