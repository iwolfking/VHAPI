package xyz.iwolfking.vaultcrackerlib.api.registry.generic.records;

import iskallia.vault.dynamodel.registry.DynamicModelRegistry;
import iskallia.vault.gear.VaultGearRarity;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.Map;

public record CustomVaultGearEntry(Item registryItem, String name,
                                   DynamicModelRegistry<?> dynamicModelRegistry, Map<VaultGearRarity, List<String>> modelRarityMap) {

}

