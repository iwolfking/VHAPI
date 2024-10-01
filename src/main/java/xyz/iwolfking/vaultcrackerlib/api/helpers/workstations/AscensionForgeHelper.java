package xyz.iwolfking.vaultcrackerlib.api.helpers.workstations;

import iskallia.vault.config.AscensionForgeConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import xyz.iwolfking.vaultcrackerlib.api.util.ItemStackUtils;

public class AscensionForgeHelper {
    public static AscensionForgeConfig.AscensionForgeListing createListing(ResourceLocation modelId, ItemStack stack, int cost) {
        return new AscensionForgeConfig.AscensionForgeListing(modelId, stack, cost);
    }

    public static AscensionForgeConfig.AscensionForgeListing createListing(ResourceLocation modelId, Item item, int amount, CompoundTag nbt, int cost) {
        return new AscensionForgeConfig.AscensionForgeListing(modelId, new ItemStack(item, amount, nbt), cost);
    }

    public static AscensionForgeConfig.AscensionForgeListing createListing(ResourceLocation modelId, Item item, int amount, String nbt, int cost) {
        return new AscensionForgeConfig.AscensionForgeListing(modelId, ItemStackUtils.createNbtItemStack(item, amount, nbt), cost);
    }


}
