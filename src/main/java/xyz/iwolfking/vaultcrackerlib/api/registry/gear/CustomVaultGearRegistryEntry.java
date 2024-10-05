package xyz.iwolfking.vaultcrackerlib.api.registry.gear;

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

    private final Map<VaultGearRarity, List<String>> modelRarityMap;

    public CustomVaultGearRegistryEntry(String id, String name, @NotNull Item registryItem, DynamicModelRegistry<?> dynamicModelRegistry, Map<VaultGearRarity, List<String>> modelRarityMap) {
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

    public Map<VaultGearRarity, List<String>> getModelRarityMap() {
        return modelRarityMap;
    }
}
