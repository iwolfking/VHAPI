package xyz.iwolfking.vhapi.api.loaders.box;

import iskallia.vault.config.entry.vending.ProductEntry;
import iskallia.vault.util.data.WeightedList;
import net.minecraft.world.item.Item;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.box.lib.MappedWeightedProductEntryConfig;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class MappedWeightedProductEntryConfigLoader extends VaultConfigProcessor<MappedWeightedProductEntryConfig> {
    Supplier<Map<String, WeightedList<ProductEntry>>> targetPool;

    public MappedWeightedProductEntryConfigLoader(Supplier<Map<String, WeightedList<ProductEntry>>> POOL, String name) {
        super(new MappedWeightedProductEntryConfig(), "loot_box/" + name);
        this.targetPool = POOL;
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(MappedWeightedProductEntryConfig config : this.CUSTOM_CONFIGS.values()) {
            for(String modKey : config.POOL.keySet()) {
                Set<Item> entriesToRemove = new HashSet<>();
                if(targetPool.get().containsKey(modKey)) {
                    config.POOL.get(modKey).forEach(productEntryEntry -> {
                        if(productEntryEntry.value.getNBT() != null && productEntryEntry.value.getNBT().contains("remove")) {
                            entriesToRemove.add(productEntryEntry.value.getItem());
                        }
                        else {
                            targetPool.get().get(modKey).add(productEntryEntry);
                        }


                    });

                    Set<ProductEntry> productsToRemove = new HashSet<>();
                    for(WeightedList.Entry<ProductEntry> entry : targetPool.get().get(modKey)) {
                        if(entriesToRemove.contains(entry.value.getItem())) {
                            productsToRemove.add(entry.value);
                        }
                    }

                    for(ProductEntry removeEntry : productsToRemove) {
                        targetPool.get().get(modKey).removeEntry(removeEntry);
                    }
                }
                else {
                    targetPool.get().put(modKey, config.POOL.get(modKey));
                }

            }
        }
        super.afterConfigsLoad(event);
    }
}
