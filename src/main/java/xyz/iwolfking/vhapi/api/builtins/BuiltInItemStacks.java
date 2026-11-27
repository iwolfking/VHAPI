package xyz.iwolfking.vhapi.api.builtins;

import iskallia.vault.init.ModItems;
import iskallia.vault.item.BoosterPackItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class BuiltInItemStacks {
    public static ItemStack deckCore(String modifier, @Nullable String modifierRoll, int count) {
        ItemStack core = new ItemStack(ModItems.DECK_SOCKET, count);
        CompoundTag tag = core.getOrCreateTag();
        tag.putString("Modifier", modifier);
        if(modifierRoll != null) {
            tag.putString("ModifierRoll", modifierRoll);
        }
        return core;
    }

    public static ItemStack boosterPack(ResourceLocation id, int count) {
        ItemStack pack = new ItemStack(ModItems.BOOSTER_PACK, count);
        BoosterPackItem.setId(pack, id.toString());
        return pack;
    }

    public static ItemStack legendaryGearPiece(Item gearPiece, String gearRolLTypePool) {
        ItemStack gear = new ItemStack(gearPiece);
        CompoundTag tag = gear.getOrCreateTag();
        tag.putBoolean("the_vault:is_legendary", true);
        tag.putString("the_vault:gear_roll_type_pool", gearRolLTypePool);
        return gear;
    }

    public static ItemStack gearWithRollType(Item gearPiece, String gearRollType) {
        ItemStack gear = new ItemStack(gearPiece);
        CompoundTag tag = gear.getOrCreateTag();
        tag.putString("the_vault:gear_roll_type", gearRollType);
        return gear;
    }

    public static ItemStack uniqueGear(Item gearPiece, String uniquePoolId) {
        ItemStack gear = new ItemStack(gearPiece);
        CompoundTag tag = gear.getOrCreateTag();
        tag.putString("the_vault:gear_unique_pool", uniquePoolId);
        tag.putString("the_vault:gear_roll_type", "Unique");
        return gear;
    }
 }
