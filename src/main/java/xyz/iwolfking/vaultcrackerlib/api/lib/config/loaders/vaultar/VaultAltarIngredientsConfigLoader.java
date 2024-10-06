package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.vaultar;

import iskallia.vault.config.altar.VaultAltarIngredientsConfig;
import iskallia.vault.config.altar.entry.AltarIngredientEntry;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.util.data.WeightedList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.Weight;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.VaultAltarIngredientsConfigAccessor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class VaultAltarIngredientsConfigLoader extends VaultConfigDataLoader<VaultAltarIngredientsConfig> {
    public VaultAltarIngredientsConfigLoader(String namespace) {
        super(new VaultAltarIngredientsConfig(), "vaultar", new HashMap<>(), namespace);
    }


    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        Set<Map.Entry<Integer, Map<String, WeightedList<AltarIngredientEntry>>>> originalMapEntries = ((VaultAltarIngredientsConfigAccessor)ModConfigs.VAULT_ALTAR_INGREDIENTS).getLevels().entrySet();
        for(ResourceLocation configLocation : this.CUSTOM_CONFIGS.keySet()) {
            if(configLocation.equals(namespaceLoc("vault_altar_ingredients"))) {
                ModConfigs.VAULT_ALTAR_INGREDIENTS = this.CUSTOM_CONFIGS.get(configLocation);
            }
            else if(configLocation.getPath().contains("overwrite")) {
                VaultAltarIngredientsConfig config = this.CUSTOM_CONFIGS.get(configLocation);
                Set<Map.Entry<Integer, Map<String, WeightedList<AltarIngredientEntry>>>> newMapEntries = ((VaultAltarIngredientsConfigAccessor)config).getLevels().entrySet();
                for(Map.Entry<Integer, Map<String, WeightedList<AltarIngredientEntry>>> newMapEntry : newMapEntries) {
                    for(Map.Entry<Integer, Map<String, WeightedList<AltarIngredientEntry>>> originalMapEntry : originalMapEntries) {
                        originalMapEntry.getValue().putAll(newMapEntry.getValue());
                    }
                }
            }
            else {
                VaultAltarIngredientsConfig config = this.CUSTOM_CONFIGS.get(configLocation);
                Set<Map.Entry<Integer, Map<String, WeightedList<AltarIngredientEntry>>>> newMapEntries = ((VaultAltarIngredientsConfigAccessor)config).getLevels().entrySet();
                for(Map.Entry<Integer, Map<String, WeightedList<AltarIngredientEntry>>> newMapEntry : newMapEntries) {
                    for(Map.Entry<Integer, Map<String, WeightedList<AltarIngredientEntry>>> originalMapEntry : originalMapEntries) {
                        if(originalMapEntry.getKey().equals(newMapEntry.getKey())) {
                            for(String key : newMapEntry.getValue().keySet()) {
                                originalMapEntry.getValue().get(key).addAll(newMapEntry.getValue().get(key));
                            }
                        }
                    }
                }
            }
        }
    }
}
