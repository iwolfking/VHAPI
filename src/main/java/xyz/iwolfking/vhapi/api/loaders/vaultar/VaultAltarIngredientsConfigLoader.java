package xyz.iwolfking.vhapi.api.loaders.vaultar;

import iskallia.vault.config.altar.VaultAltarIngredientsConfig;
import iskallia.vault.config.altar.entry.AltarIngredientEntry;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.util.data.WeightedList;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.VaultAltarIngredientsConfigAccessor;

import java.util.Map;
import java.util.Set;

public class VaultAltarIngredientsConfigLoader extends VaultConfigProcessor<VaultAltarIngredientsConfig> {
    public VaultAltarIngredientsConfigLoader() {
        super(new VaultAltarIngredientsConfig(), "vaultar");
    }


    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        Set<Map.Entry<Integer, Map<String, WeightedList<AltarIngredientEntry>>>> originalMapEntries = ((VaultAltarIngredientsConfigAccessor)ModConfigs.VAULT_ALTAR_INGREDIENTS).getLevels().entrySet();
        for(ResourceLocation configLocation : this.CUSTOM_CONFIGS.keySet()) {
            if(configLocation.getPath().equals("vault_altar_ingredients")) {
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
        super.afterConfigsLoad(event);
    }
}
