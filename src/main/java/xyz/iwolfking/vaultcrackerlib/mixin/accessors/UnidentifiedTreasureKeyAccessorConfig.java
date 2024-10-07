package xyz.iwolfking.vaultcrackerlib.mixin.accessors;

import iskallia.vault.config.UnidentifiedTreasureKeyConfig;
import iskallia.vault.config.entry.vending.ProductEntry;
import iskallia.vault.util.data.WeightedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = UnidentifiedTreasureKeyConfig.class, remap = false)
public interface UnidentifiedTreasureKeyAccessorConfig {
    @Accessor("treasureKeys")
    public WeightedList<ProductEntry> getTreasureKeys();

}
