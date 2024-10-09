package xyz.iwolfking.vhapi.api.helpers.workstations.market;

import iskallia.vault.config.OmegaSoulShardConfig;
import iskallia.vault.config.SoulShardConfig;
import iskallia.vault.config.entry.ItemEntry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

@Deprecated
public class ShardTradeHelper {
    public static SoulShardConfig.ShardTrade normalEntry(ItemStack stack, int minPrice, int maxPrice) {
        return new SoulShardConfig.ShardTrade(new ItemEntry(stack), minPrice, maxPrice);
    }

    public static SoulShardConfig.ShardTrade normalEntry(String item, int amount, String nbt, int minPrice, int maxPrice) {
        return new SoulShardConfig.ShardTrade(new ItemEntry(item, amount , nbt), minPrice, maxPrice);
    }

    public static SoulShardConfig.ShardTrade normalEntry(ItemLike item, int amount, int minPrice, int maxPrice) {
        return new SoulShardConfig.ShardTrade(new ItemEntry(item, amount), minPrice, maxPrice);
    }

    public static SoulShardConfig.ShardTrade normalEntry(ResourceLocation item, int amount, CompoundTag nbt, int minPrice, int maxPrice) {
        return new SoulShardConfig.ShardTrade(new ItemEntry(item, amount, nbt), minPrice, maxPrice);
    }

    public static OmegaSoulShardConfig.ShardTrade omegaEntry(ItemStack stack, int minPrice, int maxPrice) {
        return new OmegaSoulShardConfig.ShardTrade(new ItemEntry(stack), minPrice, maxPrice);
    }

    public static OmegaSoulShardConfig.ShardTrade omegaEntry(String item, int amount, String nbt, int minPrice, int maxPrice) {
        return new OmegaSoulShardConfig.ShardTrade(new ItemEntry(item, amount , nbt), minPrice, maxPrice);
    }

    public static OmegaSoulShardConfig.ShardTrade omegaEntry(ItemLike item, int amount, int minPrice, int maxPrice) {
        return new OmegaSoulShardConfig.ShardTrade(new ItemEntry(item, amount), minPrice, maxPrice);
    }

    public static OmegaSoulShardConfig.ShardTrade omegaEntry(ResourceLocation item, int amount, CompoundTag nbt, int minPrice, int maxPrice) {
        return new OmegaSoulShardConfig.ShardTrade(new ItemEntry(item, amount, nbt), minPrice, maxPrice);
    }
}
