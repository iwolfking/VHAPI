package xyz.iwolfking.vaultcrackerlib.mixin.configs.altar;

import iskallia.vault.config.altar.VaultAltarIngredientsConfig;
import iskallia.vault.config.altar.entry.AltarIngredientEntry;
import iskallia.vault.config.entry.LevelEntryMap;
import iskallia.vault.util.data.WeightedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.Patchers;

import java.util.Map;

@Mixin(value = VaultAltarIngredientsConfig.class, remap = false)
public class MixinVaultAltarIngredientsConfig {

    @Shadow private LevelEntryMap<Map<String, WeightedList<AltarIngredientEntry>>> LEVELS;

    @Inject(method = "getEntries", at = @At("HEAD"))
    private void injectNewEntries(int vaultLevel, CallbackInfoReturnable<Map<String, AltarIngredientEntry>> cir) {
        Patchers.VAULT_ALTAR_INGREDIENT_PATCHER.doPatches(this.LEVELS);
    }
}
