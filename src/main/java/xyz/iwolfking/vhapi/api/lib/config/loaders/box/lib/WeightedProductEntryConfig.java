package xyz.iwolfking.vhapi.api.lib.config.loaders.box.lib;

import com.google.gson.annotations.Expose;
import iskallia.vault.config.Config;
import iskallia.vault.config.entry.vending.ProductEntry;
import iskallia.vault.util.data.WeightedList;

public class WeightedProductEntryConfig extends Config {

    @Expose
    public WeightedList<ProductEntry> POOL;

    @Override
    public String getName() {
        return "weighted_product_entry";
    }

    @Override
    protected void reset() {

    }
}
