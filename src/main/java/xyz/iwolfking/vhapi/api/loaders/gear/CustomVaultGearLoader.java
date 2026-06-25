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
        for (ResourceLocation configKey : this.CUSTOM_CONFIGS.keySet()) {
            String strippedPath = ResourceLocUtils.getStrippedPath(configKey);
            VaultGearTierConfig tierConfig = this.CUSTOM_CONFIGS.get(configKey);

            if (ModConfigs.VAULT_GEAR_CONFIG.containsKey(VaultMod.id(strippedPath))) {
                VaultGearTierConfig originalTierConfig = ModConfigs.VAULT_GEAR_CONFIG.get(VaultMod.id(strippedPath));

                var currentModifierMap = ((VaultGearTierConfigAccessor) tierConfig).getModifierGroup();
                var originalModifierMap = ((VaultGearTierConfigAccessor) originalTierConfig).getModifierGroup();

                currentModifierMap.forEach((incomingGroup, incomingAttributeGroup) -> {
                    VaultGearTierConfig.ModifierAffixTagGroup targetGroup = null;

                    for (VaultGearTierConfig.ModifierAffixTagGroup existingGroup : originalModifierMap.keySet()) {
                        String existingName = existingGroup != null ? existingGroup.toString() : "null";
                        String incomingName = incomingGroup != null ? incomingGroup.toString() : "null";

                        if (existingName.equalsIgnoreCase(incomingName)) {
                            targetGroup = existingGroup;
                            break;
                        }
                    }

                    if (targetGroup == null && incomingGroup != null) {
                        targetGroup = incomingGroup;
                    }

                    if (targetGroup != null) {
                        if (configKey.getPath().contains("overwrite")) {
                            ModConfigs.VAULT_GEAR_CONFIG.put(VaultMod.id(strippedPath), tierConfig);
                        }
                        else if (configKey.getPath().contains("replace")) {
                            originalModifierMap.put(targetGroup, incomingAttributeGroup);
                        }
                        else if (configKey.getPath().contains("remove")) {
                            List<ResourceLocation> attributesToRemove = new ArrayList<>();
                            incomingAttributeGroup.forEach(modifierTiers -> attributesToRemove.add(modifierTiers.getAttribute()));

                            var existingAttributes = originalModifierMap.get(targetGroup);
                            if (existingAttributes != null) {
                                existingAttributes.removeIf(modifierTiers -> attributesToRemove.contains(modifierTiers.getAttribute()));
                            }
                        }
                        else {
                            var existingAttributes = originalModifierMap.get(targetGroup);
                            if (existingAttributes != null) {
                                existingAttributes.addAll(incomingAttributeGroup);
                            } else {
                                originalModifierMap.put(targetGroup, incomingAttributeGroup);
                            }
                        }
                    }
                });
            }
        }
        super.afterConfigsLoad(event);
    }

}
