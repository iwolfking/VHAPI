package xyz.iwolfking.vhapi.mixin.registry.sprites;

import iskallia.vault.client.atlas.ResourceTextureAtlasHolder;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = ResourceTextureAtlasHolder.class, remap = false)
public class MixinResourceTextureAtlasHolder
{
    @Shadow @Final protected ResourceLocation resourceLocation;

    /**
     * @author iwolfking
     * @reason Disable filtering resource locations.
     */
    @Overwrite
    protected boolean filterResourceLocation(ResourceLocation resourceLocation) {
        return true;
    }
}
