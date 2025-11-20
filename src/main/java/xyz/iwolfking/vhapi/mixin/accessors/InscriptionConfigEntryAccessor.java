package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.InscriptionConfig;
import iskallia.vault.core.world.roll.IntRoll;
import iskallia.vault.item.data.InscriptionData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(value = InscriptionConfig.Entry.class, remap = false)
public interface InscriptionConfigEntryAccessor {
    @Accessor
    IntRoll getModel();

    @Accessor
    IntRoll getSize();

    @Accessor
    Integer getColor();

    @Accessor
    List<InscriptionData.Entry> getEntries();
}
