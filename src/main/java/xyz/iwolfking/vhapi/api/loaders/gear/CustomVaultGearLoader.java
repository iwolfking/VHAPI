package xyz.iwolfking.vhapi.api.loaders.gear;


import iskallia.vault.VaultMod;

import iskallia.vault.config.gear.VaultGearTierConfig;

import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.api.util.ResourceLocUtils;
import xyz.iwolfking.vhapi.mixin.accessors.VaultGearTierConfigAccessor;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = "vhapi", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CustomVaultGearLoader extends VaultConfigProcessor<VaultGearTierConfig> {


    public CustomVaultGearLoader() {
        super(new VaultGearTierConfig(VaultMod.id("test")), "gear/gear_modifiers");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        VaultGearTierConfig.registerConfigs();
        for(ResourceLocation configKey : this.CUSTOM_CONFIGS.keySet()) {
            String strippedPath = ResourceLocUtils.getStrippedPath(configKey);
            VaultGearTierConfig tierConfig = this.CUSTOM_CONFIGS.get(configKey);
            if(ModConfigs.VAULT_GEAR_CONFIG.containsKey(VaultMod.id(strippedPath))) {
                VaultGearTierConfig originalTierConfig = ModConfigs.VAULT_GEAR_CONFIG.get(VaultMod.id(strippedPath));
                for(VaultGearTierConfig.ModifierAffixTagGroup affixGroup : ((VaultGearTierConfigAccessor)tierConfig).getModifierGroup().keySet()) {
                    //Replaces an entire config with the new one
                    if(configKey.getPath().contains("overwrite")) {
                        ModConfigs.VAULT_GEAR_CONFIG.put(VaultMod.id(strippedPath), tierConfig);
                    }
                    //Replaces an entire affix group with the new ones
                    else if(configKey.getPath().contains("replace")) {
                        ((VaultGearTierConfigAccessor)originalTierConfig).getModifierGroup().put(affixGroup, (((VaultGearTierConfigAccessor) tierConfig).getModifierGroup().get(affixGroup)));
                    }
                    //Find matches for attribute in group and remove if it matches
                    else if(configKey.getPath().contains("remove")) {
                        List<ResourceLocation> attributesToRemoveFromGroup = new ArrayList<>();
                        ((VaultGearTierConfigAccessor) tierConfig).getModifierGroup().get(affixGroup).forEach(modifierTiers -> {
                            attributesToRemoveFromGroup.add(modifierTiers.getAttribute());
                        });
                        ((VaultGearTierConfigAccessor)originalTierConfig).getModifierGroup().get(affixGroup).removeIf(modifierTiers -> attributesToRemoveFromGroup.contains(modifierTiers.getAttribute()));
                    }
                    //Add all modifiers to original config
                    else {
                        ((VaultGearTierConfigAccessor)originalTierConfig).getModifierGroup().get(affixGroup).addAll(((VaultGearTierConfigAccessor) tierConfig).getModifierGroup().get(affixGroup));
                    }
                }
            }
        }
        super.afterConfigsLoad(event);
    }

}
