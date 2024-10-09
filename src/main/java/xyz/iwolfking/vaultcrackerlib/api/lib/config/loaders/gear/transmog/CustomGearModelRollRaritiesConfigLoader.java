package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear.transmog;

import iskallia.vault.config.GearModelRollRaritiesConfig;
import iskallia.vault.gear.VaultGearRarity;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import xyz.iwolfking.vaultcrackerlib.api.data.CustomGearModelRolls;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear.transmog.lib.CustomGearModelRollRaritiesConfig;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.GearModelRollRaritiesAccessor;

import java.util.HashMap;

@Mod.EventBusSubscriber(modid = "vaultcrackerlib", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CustomGearModelRollRaritiesConfigLoader extends VaultConfigDataLoader<CustomGearModelRollRaritiesConfig> {

    public CustomGearModelRollRaritiesConfigLoader(String namespace) {
        super(new CustomGearModelRollRaritiesConfig(), "gear/custom/model_rolls", new HashMap<>(), namespace);
    }


    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(CustomGearModelRollRaritiesConfig config : this.CUSTOM_CONFIGS.values()) {
            if(CustomGearModelRolls.CUSTOM_MODEL_ROLLS_MAP.containsKey(config.itemRegistryName)) {
                for(VaultGearRarity rarity : VaultGearRarity.values()) {
                    if(CustomGearModelRolls.CUSTOM_MODEL_ROLLS_MAP.get(config.itemRegistryName).containsKey(rarity)){
                        CustomGearModelRolls.CUSTOM_MODEL_ROLLS_MAP.get(config.itemRegistryName).get(rarity).addAll(config.MODEL_ROLLS.get(rarity));
                    }
                    else {
                        CustomGearModelRolls.CUSTOM_MODEL_ROLLS_MAP.get(config.itemRegistryName).put(rarity, config.MODEL_ROLLS.get(rarity));
                    }
                }
            }
            else {
                CustomGearModelRolls.CUSTOM_MODEL_ROLLS_MAP.put(config.itemRegistryName, config.MODEL_ROLLS);
            }
        }
    }

}
