package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.TooltipConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(value = TooltipConfig.class, remap = false)
public interface TooltipConfigAccessor {
    @Accessor("tooltips")
    public List<TooltipConfig.TooltipEntry> getTooltips();
}
