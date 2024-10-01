package xyz.iwolfking.vaultcrackerlib.api.patching.configs.lib.custom.altar;

import iskallia.vault.config.altar.entry.AltarIngredientEntry;
import iskallia.vault.config.entry.LevelEntryMap;
import iskallia.vault.util.data.WeightedList;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.Patchers;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.lib.BasicPatcher;

import java.util.Map;

public class VaultAltarIngredientInjector extends BasicPatcher {
    public void doPatches(LevelEntryMap<Map<String, WeightedList<AltarIngredientEntry>>> levelEntryMap) {
        if(!this.isPatched()) {
            Patchers.RESOURCE_VAULT_ALTAR_INGREDIENT_PATCHER.doPatches(levelEntryMap);
            Patchers.MOB_VAULT_ALTAR_INGREDIENT_PATCHER.doPatches(levelEntryMap);
            Patchers.FARMABLE_VAULT_ALTAR_INGREDIENT_PATCHER.doPatches(levelEntryMap);
            Patchers.MISC_VAULT_ALTAR_INGREDIENT_PATCHER.doPatches(levelEntryMap);
        }
    }
}
