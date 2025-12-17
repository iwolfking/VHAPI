package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.card.DeckModifiersConfig;
import iskallia.vault.core.util.WeightedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = DeckModifiersConfig.class, remap = false)
public interface DeckModifiersConfigAccessor {
    @Accessor("pools")
    Map<String, WeightedList<String>> getPools();
}
