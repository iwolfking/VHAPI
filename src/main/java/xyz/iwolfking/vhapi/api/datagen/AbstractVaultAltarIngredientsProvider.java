package xyz.iwolfking.vhapi.api.datagen;

import com.google.gson.JsonObject;
import iskallia.vault.config.AbilitiesGUIConfig;
import iskallia.vault.config.altar.VaultAltarIngredientsConfig;
import iskallia.vault.config.altar.entry.AltarIngredientEntry;
import iskallia.vault.config.entry.DescriptionData;
import iskallia.vault.config.entry.LevelEntryMap;
import iskallia.vault.util.data.WeightedList;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.mixin.accessors.AbilitiesGUIConfigSpecializationStyleAccessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractVaultAltarIngredientsProvider extends AbstractVaultConfigDataProvider {
    protected AbstractVaultAltarIngredientsProvider(DataGenerator generator, String modid) {
        super(generator, modid, "vaultar");
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Vault Altar Ingredients Data Provider";
    }

    public static class VaultAltarIngredientsBuilder {
        public LevelEntryMap<Map<String, WeightedList<AltarIngredientEntry>>> LEVELS = new LevelEntryMap<>();

        public VaultAltarIngredientsConfig build() {
            VaultAltarIngredientsConfig newConfig = new VaultAltarIngredientsConfig();
            return newConfig;
        }

    }
}
