package xyz.iwolfking.vhapi.integration.jevh.lib;

import net.minecraft.world.item.ItemStack;
import java.util.List;

public record CrystalWorkbenchRecipe(ItemStack input, ItemStack output, List<String> tooltips) {

    public CrystalWorkbenchRecipe(ItemStack input, ItemStack output) {
        this(input, output, List.of());
    }
}