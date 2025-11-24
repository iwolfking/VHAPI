package xyz.iwolfking.vhapi.api.datagen.lib.loot;

import iskallia.vault.config.entry.vending.ProductEntry;
import iskallia.vault.util.data.WeightedList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class ProductEntryListBuilder {
            WeightedList<ProductEntry> entries = new WeightedList<>();

            public ProductEntryListBuilder add(Item item, int weight) {
                entries.add(new ProductEntry(item, 1, (CompoundTag)null), weight);
                return this;
            }

            public ProductEntryListBuilder add(ItemStack stack, int weight) {
                entries.add(new ProductEntry(stack.getItem(), stack.getCount(), stack.getTag()), weight);
                return this;
            }

            public ProductEntryListBuilder add(Item item, int amount, @Nullable CompoundTag tag, int weight) {
                entries.add(new ProductEntry(item, amount, tag), weight);
                return this;
            }

            public ProductEntryListBuilder add(Item item, int amountMin, int amountMax, @Nullable CompoundTag tag, int weight) {
                entries.add(new ProductEntry(item, amountMin, amountMax, tag), weight);
                return this;
            }

            public WeightedList<ProductEntry> build() {
                return entries;
            }
        }