package xyz.iwolfking.vaultcrackerlib.mixin.accessors;

import iskallia.vault.dynamodel.registry.DynamicModelRegistry;
import iskallia.vault.init.ModDynamicModels;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Map;

@Mixin(value = DynamicModelRegistry.class, remap = false)
public interface DynamicModelRegistryAccessor<M> {
    @Accessor("RESOURCES")
    public Map<ResourceLocation, M> getResources();
}
