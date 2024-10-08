package xyz.iwolfking.vaultcrackerlib.mixin.accessors;

import com.google.gson.annotations.Expose;
import iskallia.vault.config.card.CardModifiersConfig;
import iskallia.vault.core.card.CardEntry;
import iskallia.vault.core.util.WeightedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = CardModifiersConfig.class, remap = false)
public interface CardModifiersConfigAccessor {
    @Accessor("values")
    public Map<String, CardEntry.Config> getValues();
    @Accessor("pools")
    public Map<String, WeightedList<String>> getPools();
}
