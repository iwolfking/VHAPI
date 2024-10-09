package xyz.iwolfking.vaultcrackerlib.api.registry;

import iskallia.vault.core.vault.VaultRegistry;
import iskallia.vault.init.ModDynamicModels;
import iskallia.vault.init.ModItems;
import iskallia.vault.item.crystal.CrystalData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.*;
import org.jetbrains.annotations.Nullable;
import xyz.iwolfking.vaultcrackerlib.api.registry.gear.CustomVaultGearRegistryEntry;

import xyz.iwolfking.vaultcrackerlib.mixin.accessors.ReforgeTagModificationFocusAccessor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class VaultGearRegistry {
    public static Supplier<IForgeRegistry<CustomVaultGearRegistryEntry>> customGearRegistry;


    public static void newRegistry(NewRegistryEvent event) {
        RegistryBuilder<CustomVaultGearRegistryEntry> builder = new RegistryBuilder<CustomVaultGearRegistryEntry>().setType(CustomVaultGearRegistryEntry.class).setName(new ResourceLocation("vaultcrackerlib", "custom_gear_registry")).add(new VaultGearRegistry.Callback());
        customGearRegistry = event.create(builder);
    }

    public static class Callback implements IForgeRegistry.AddCallback<CustomVaultGearRegistryEntry> {
        @Override
        public void onAdd(IForgeRegistryInternal<CustomVaultGearRegistryEntry> iForgeRegistryInternal, RegistryManager registryManager, int i, CustomVaultGearRegistryEntry customGearRegistryEntry, @Nullable CustomVaultGearRegistryEntry v1) {
            if(ReforgeTagModificationFocusAccessor.getItemToName() != null) {
                ReforgeTagModificationFocusAccessor.getItemToName().put(customGearRegistryEntry.getRegistryItem(), customGearRegistryEntry.getName());
            }
            ModDynamicModels.REGISTRIES.associate(customGearRegistryEntry.getRegistryItem(), customGearRegistryEntry.getDynamicModelRegistry());
        }
    }

    public static ItemLike[] getItemLikes() {
        Set<ItemLike> itemLikeList = new HashSet<ItemLike>();
        for(CustomVaultGearRegistryEntry entry : customGearRegistry.get().getValues()) {
            itemLikeList.add(entry.getRegistryItem());
        }
        itemLikeList.addAll(List.of(ModItems.HELMET, ModItems.CHESTPLATE, ModItems.LEGGINGS, ModItems.BOOTS, ModItems.AXE, ModItems.SWORD, ModItems.SHIELD, ModItems.IDOL_BENEVOLENT, ModItems.IDOL_OMNISCIENT, ModItems.IDOL_TIMEKEEPER, ModItems.IDOL_MALEVOLENCE, ModItems.MAGNET, ModItems.WAND, ModItems.FOCUS));
        return itemLikeList.toArray(new ItemLike[0]);
    }
}
