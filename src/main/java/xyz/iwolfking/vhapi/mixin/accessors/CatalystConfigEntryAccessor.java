package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.CatalystConfig;
import iskallia.vault.config.InscriptionConfig;
import iskallia.vault.core.world.roll.IntRoll;
import iskallia.vault.item.data.InscriptionData;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(value = CatalystConfig.Entry.class, remap = false)
public interface CatalystConfigEntryAccessor {
    @Accessor("model")
    int getModel();

    @Accessor("size")
    IntRoll getSize();

    @Accessor("modifiers")
    List<ResourceLocation> getModifiers();
}
