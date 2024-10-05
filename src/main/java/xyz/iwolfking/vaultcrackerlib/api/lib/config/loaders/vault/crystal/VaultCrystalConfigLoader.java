package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.vault.crystal;

import iskallia.vault.config.VaultCrystalConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraftforge.fml.common.Mod;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.VaultCrystalConfigAccessor;

import java.util.*;
@Mod.EventBusSubscriber(modid = "vaultcrackerlib", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class VaultCrystalConfigLoader extends VaultConfigDataLoader<VaultCrystalConfig> {
    public static final VaultCrystalConfig instance = new VaultCrystalConfig();

    public VaultCrystalConfigLoader(String namespace) {
        super(instance, "vault/crystal", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(VaultCrystalConfig config : this.CUSTOM_CONFIGS.values()) {
            System.out.println("Found a config!");
            if(config.LAYOUTS != null) {
                ModConfigs.VAULT_CRYSTAL.LAYOUTS.addAll(config.LAYOUTS);
            }
            //Motes
            if(config.MOTES != null) {
                ModConfigs.VAULT_CRYSTAL.MOTES.clarityLevelCost = config.MOTES.clarityLevelCost;
                ModConfigs.VAULT_CRYSTAL.MOTES.purityLevelCost = config.MOTES.purityLevelCost;
                ModConfigs.VAULT_CRYSTAL.MOTES.sanctityLevelCost = config.MOTES.sanctityLevelCost;
            }

            //Modifier Stability
            if(config.MODIFIER_STABILITY != null) {
                ModConfigs.VAULT_CRYSTAL.MODIFIER_STABILITY.curseColor = config.MODIFIER_STABILITY.curseColor;
                ModConfigs.VAULT_CRYSTAL.MODIFIER_STABILITY.exhaustProbability = config.MODIFIER_STABILITY.exhaustProbability;
                ModConfigs.VAULT_CRYSTAL.MODIFIER_STABILITY.instabilityPerCraft = config.MODIFIER_STABILITY.instabilityPerCraft;
            }

            //Themes
            //Unfortunately can't add to individual pools due to ThemeEntry being private.
            if(config.THEMES != null) {
                ModConfigs.VAULT_CRYSTAL.THEMES.putAll(config.THEMES);
            }

            //Objectives
            //Unfortunately can't add to individual pools due to ObjectiveEntry being private.
            if(config.OBJECTIVES != null) {
                ModConfigs.VAULT_CRYSTAL.OBJECTIVES.putAll(config.OBJECTIVES);
            }

            //Times
            //Unfortunately can't add to individual pools due to TimeEntry being private.
            if(config.TIMES != null) {
                ModConfigs.VAULT_CRYSTAL.TIMES.putAll(config.TIMES);
            }

            //Properties
            //Unfortunately can't add to individual pools due to PropertyEntry being private.
            if(config.PROPERTIES != null) {
                ModConfigs.VAULT_CRYSTAL.PROPERTIES.putAll(config.PROPERTIES);
            }

            //Seals
            //Has same caveat as both, multiple overrides to same pool with overwrite each other.
            if(((VaultCrystalConfigAccessor)config).getSeals() != null) {
                ((VaultCrystalConfigAccessor)ModConfigs.VAULT_CRYSTAL).getSeals().putAll(((VaultCrystalConfigAccessor) config).getSeals());
            }

        }
    }
}
