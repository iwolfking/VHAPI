package xyz.iwolfking.vaultcrackerlib.api.util;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemStackUtils {
    public static ItemStack createNbtItemStack(Item item, int count, String nbtString) {
        ItemStack stack = item.getDefaultInstance();
        stack.setCount(count);

        CompoundTag nbt;

        try {
            nbt = net.minecraft.nbt.TagParser.parseTag(nbtString);
        } catch (CommandSyntaxException e) {
           throw new RuntimeException();
        }

        stack.setTag(nbt);

        return stack;
    }
}
