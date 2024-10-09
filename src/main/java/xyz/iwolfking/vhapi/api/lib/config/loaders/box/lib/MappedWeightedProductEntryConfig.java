package xyz.iwolfking.vhapi.api.lib.config.loaders.box.lib;

import com.google.gson.annotations.Expose;
import iskallia.vault.config.Config;
import iskallia.vault.config.entry.vending.ProductEntry;
import iskallia.vault.util.data.WeightedList;

import java.util.HashMap;
import java.util.Map;

public class MappedWeightedProductEntryConfig extends Config {
    @Expose
    public Map<String, WeightedList<ProductEntry>> POOL = new HashMap<>();

    @Override
    public String getName() {
        return "mapped_weighted_product_entry";
    }

    @Override
    protected void reset() {

    }
}
