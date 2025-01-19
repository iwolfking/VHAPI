package xyz.iwolfking.vhapi.api.registry.gear;

import iskallia.vault.dynamodel.registry.DynamicModelRegistry;
import iskallia.vault.gear.VaultGearRarity;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class CustomVaultGearRegistryEntry extends ForgeRegistryEntry<CustomVaultGearRegistryEntry> {

    private final String id;

    private final String name;

    private final Item registryItem;

    private final DynamicModelRegistry<?> dynamicModelRegistry;

    private final Map<String, List<String>> modelRarityMap;

    /**
     *
     * @param id The internal id to use for the item (ex. "axe", "dagger", "trident", etc.)
     * @param name The name of the item, used for display in Faceted Focus.
     * @param registryItem The class of the gear item.
     * @param dynamicModelRegistry A registry that will contain all of the transmog models for the gear piece.
     * @param modelRarityMap A map of VaultGearRarity to a list of transmog model names.
     */
    public CustomVaultGearRegistryEntry(String id, String name, @NotNull Item registryItem, DynamicModelRegistry<?> dynamicModelRegistry, Map<String, List<String>> modelRarityMap) {
        this.name = name;
        this.registryItem = registryItem;
        this.dynamicModelRegistry = dynamicModelRegistry;
        this.modelRarityMap = modelRarityMap;
        this.id = id;
        this.setRegistryName(id);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Item getRegistryItem() {
        return registryItem;
    }

    public DynamicModelRegistry<?> getDynamicModelRegistry() {
        return dynamicModelRegistry;
    }

    public Map<String, List<String>> getModelRarityMap() {
        return modelRarityMap;
    }
}
