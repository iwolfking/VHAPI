package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.LootInfoConfig;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import java.util.Map;
import java.util.Set;

@Mixin(value = LootInfoConfig.class, remap = false)
public interface LootInfoConfigAccessor {
    @Accessor("lootInfoMap")
    Map<ResourceLocation, LootInfoConfig.LootInfo> getLootInfoMapModifiable();

    @Accessor("excludeFromTooltipSet")
    Set<ResourceLocation> getExcludeFromTooltipSet();
}
