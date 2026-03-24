package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.VaultDiffuserConfig;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = VaultDiffuserConfig.class, remap = false)
public interface VaultDiffuserConfigAccessor {
    @Accessor("diffuserOutputMap")
    void setDiffuserOutputMap(Map<ResourceLocation, Integer> map);
}
