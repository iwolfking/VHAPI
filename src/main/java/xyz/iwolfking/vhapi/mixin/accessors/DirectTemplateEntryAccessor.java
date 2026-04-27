package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.core.world.template.data.DirectTemplateEntry;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = DirectTemplateEntry.class, remap = false)
public interface DirectTemplateEntryAccessor {
    @Accessor("template")
    ResourceLocation getTemplateID();
}
