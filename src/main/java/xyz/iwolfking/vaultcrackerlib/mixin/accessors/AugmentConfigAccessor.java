package xyz.iwolfking.vaultcrackerlib.mixin.accessors;

import iskallia.vault.config.AugmentConfig;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = AugmentConfig.class, remap = false)
public interface AugmentConfigAccessor {
    @Accessor("drops")
    public Map<ResourceLocation, Map<String, Integer>> getDrops();

    @Accessor("drops")
    public void setDrops(Map<ResourceLocation, Map<String, Integer>> drops);
}
