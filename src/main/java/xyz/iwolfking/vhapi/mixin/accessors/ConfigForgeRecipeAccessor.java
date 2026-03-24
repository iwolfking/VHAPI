package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.entry.ItemEntry;
import iskallia.vault.config.entry.recipe.ConfigForgeRecipe;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = ConfigForgeRecipe.class, remap = false)
public interface ConfigForgeRecipeAccessor {
    @Accessor("id")
    @Mutable
    void setId(ResourceLocation id);

    @Accessor("output")
    @Mutable
    void setOutput(ItemEntry output);
}
