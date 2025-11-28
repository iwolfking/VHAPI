package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.SkillScrollConfig;
import iskallia.vault.config.VaultMapRoomIconsConfig;
import iskallia.vault.util.data.WeightedList;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Map;

@Mixin(value = VaultMapRoomIconsConfig.class, remap = false)
public interface VaultMapRoomIconsConfigAccessor {
    @Accessor("roomIcons")
    void setRoomIcons(Map<ResourceLocation, List<ResourceLocation>> icons);

}
