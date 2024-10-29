package xyz.iwolfking.vhapi.api.loaders.vault.crystal;

import iskallia.vault.config.VaultCrystalConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraftforge.fml.common.Mod;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.VaultCrystalConfigAccessor;

@Mod.EventBusSubscriber(modid = "vhapi", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class VaultCrystalConfigLoader extends VaultConfigProcessor<VaultCrystalConfig> {


    public VaultCrystalConfigLoader() {
        super(new VaultCrystalConfig(), "vault/crystal");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(VaultCrystalConfig config : this.CUSTOM_CONFIGS.values()) {
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
        super.afterConfigsLoad(event);
    }
}
