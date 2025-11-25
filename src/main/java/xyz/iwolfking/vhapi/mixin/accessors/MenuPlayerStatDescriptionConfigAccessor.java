package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.MenuPlayerStatDescriptionConfig;
import iskallia.vault.config.PreBuiltToolConfig;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;
import java.util.TreeMap;

@Mixin(value = MenuPlayerStatDescriptionConfig.class, remap = false)
public interface MenuPlayerStatDescriptionConfigAccessor {
    @Accessor("MOD_GEAR_ATTRIBUTE_DESCRIPTIONS")
    void setGearDescriptions(TreeMap<ResourceLocation, String> descriptions);

}
