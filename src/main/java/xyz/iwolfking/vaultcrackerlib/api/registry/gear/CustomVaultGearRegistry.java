package xyz.iwolfking.vaultcrackerlib.api.registry.gear;

import iskallia.vault.init.ModItems;
import net.minecraft.world.level.ItemLike;
import xyz.iwolfking.vaultcrackerlib.api.registry.generic.records.CustomVaultGearEntry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomVaultGearRegistry {
    private static final List<CustomVaultGearEntry> CUSTOM_VAULT_GEAR_ENTRIES = new ArrayList<>();

    public static void addEntry(CustomVaultGearEntry entry) {
        CUSTOM_VAULT_GEAR_ENTRIES.add(entry);
    }

    public static int getSize() {
        return CUSTOM_VAULT_GEAR_ENTRIES.size();
    }

    public static void registerAllGearEntries() {

    }

    public static ItemLike[] getItemLikes() {
        Set<ItemLike> itemLikeList = new HashSet<ItemLike>();
        for(CustomVaultGearEntry entry : CUSTOM_VAULT_GEAR_ENTRIES) {
            itemLikeList.add(entry.registryItem());
        }
        itemLikeList.addAll(List.of(ModItems.HELMET, ModItems.CHESTPLATE, ModItems.LEGGINGS, ModItems.BOOTS, ModItems.AXE, ModItems.SWORD, ModItems.SHIELD, ModItems.IDOL_BENEVOLENT, ModItems.IDOL_OMNISCIENT, ModItems.IDOL_TIMEKEEPER, ModItems.IDOL_MALEVOLENCE, ModItems.MAGNET, ModItems.WAND, ModItems.FOCUS));
        return itemLikeList.toArray(new ItemLike[0]);
    }

    public static List<CustomVaultGearEntry> getCustomGearEntries() {
        return CUSTOM_VAULT_GEAR_ENTRIES;
    }
}
