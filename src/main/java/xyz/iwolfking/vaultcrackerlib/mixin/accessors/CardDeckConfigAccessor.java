package xyz.iwolfking.vaultcrackerlib.mixin.accessors;

import iskallia.vault.config.card.CardDeckConfig;
import org.checkerframework.checker.units.qual.A;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = CardDeckConfig.class, remap = false)
public interface CardDeckConfigAccessor {
    @Accessor("values")
    public Map<String, CardDeckConfig.Entry> getValues();
}
