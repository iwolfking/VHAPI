package xyz.iwolfking.vhapi.api.loaders.box;

import iskallia.vault.config.entry.vending.ProductEntry;
import iskallia.vault.util.data.WeightedList;
import net.minecraft.world.item.Item;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.box.lib.WeightedProductEntryConfig;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class WeightedProductEntryConfigLoader extends VaultConfigProcessor<WeightedProductEntryConfig> {

    Supplier<WeightedList<ProductEntry>> targetPool;

    public WeightedProductEntryConfigLoader(Supplier<WeightedList<ProductEntry>> POOL, String name) {
        super(new WeightedProductEntryConfig(), "loot_box/" + name);
        this.targetPool = POOL;
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(WeightedProductEntryConfig config : this.CUSTOM_CONFIGS.values()) {
            Set<Item> entriesToRemove = new HashSet<>();
            config.POOL.forEach((productEntry, weight) -> {
                if(productEntry.getNBT().contains("remove")) {
                    entriesToRemove.add(productEntry.getItem());
                }
                else {
                    targetPool.get().add(productEntry, (Integer) weight);
                }
            });
            Set<ProductEntry> productsToRemove = new HashSet<>();
            for(WeightedList.Entry<ProductEntry> entry : targetPool.get()) {
                if(entriesToRemove.contains(entry.value.getItem())) {
                    productsToRemove.add(entry.value);
                }
            }
            for(ProductEntry entry : productsToRemove) {
                targetPool.get().removeEntry(entry);
            }
        }
        super.afterConfigsLoad(event);
    }
}
