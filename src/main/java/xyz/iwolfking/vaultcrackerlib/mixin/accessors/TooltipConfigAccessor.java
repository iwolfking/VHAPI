package xyz.iwolfking.vaultcrackerlib.mixin.accessors;

import iskallia.vault.config.TooltipConfig;
import net.minecraftforge.fml.common.Mod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Map;

@Mixin(value = TooltipConfig.class, remap = false)
public interface TooltipConfigAccessor {
    @Accessor("tooltips")
    public List<TooltipConfig.TooltipEntry> getTooltips();
}
