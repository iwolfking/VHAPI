package xyz.iwolfking.vaultcrackerlib.api.util;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemStackUtils {
    /**
     * Helpful method used for creating an ItemStack from a string of NBT, useful for creating Title Scroll items in Ascension Forge, for example.
     *
     * @param item The type of item to create an ItemStack of.
     * @param count The size of the ItemStack.
     * @param nbtString The NBT string that will be added to the new Itemstack.
     * @return An ItemStack of item with it's size set to count and with the NBT of nbtString.
     */
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
