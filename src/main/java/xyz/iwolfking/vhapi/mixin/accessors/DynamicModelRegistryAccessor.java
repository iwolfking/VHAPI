package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.dynamodel.registry.DynamicModelRegistry;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = DynamicModelRegistry.class, remap = false)
public interface DynamicModelRegistryAccessor<M> {
    @Accessor("RESOURCES")
    public Map<ResourceLocation, M> getResources();
}
