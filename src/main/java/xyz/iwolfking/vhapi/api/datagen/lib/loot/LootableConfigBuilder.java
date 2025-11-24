package xyz.iwolfking.vhapi.api.datagen.lib.loot;

import iskallia.vault.config.Config;
import iskallia.vault.config.entry.vending.ProductEntry;
import iskallia.vault.util.data.WeightedList;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class LootableConfigBuilder<T extends Config> extends VaultConfigBuilder<T> {
    public WeightedList<ProductEntry> entries = new WeightedList<>();

    public LootableConfigBuilder(Supplier<T> factory) {
        super(factory);
    }

    public LootableConfigBuilder<T> addItems(Consumer<ProductEntryListBuilder> builderConsumer) {
        ProductEntryListBuilder builder = new ProductEntryListBuilder();
        builderConsumer.accept(builder);
        entries.addAll(builder.build());
        return this;
    }
}
