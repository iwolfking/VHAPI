package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.InscriptionConfig;
import iskallia.vault.core.util.WeightedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = InscriptionConfig.Pool.class, remap = false)
public interface InscriptionConfigPoolAccessor {
    @Accessor
    WeightedList<InscriptionConfig.Entry> getPool();
}
