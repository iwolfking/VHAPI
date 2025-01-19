package xyz.iwolfking.vhapi.api.loaders.gear.transmog;

import iskallia.vault.gear.VaultGearRarity;
import net.minecraftforge.fml.common.Mod;
import xyz.iwolfking.vhapi.api.data.api.CustomGearModelRolls;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.gear.transmog.lib.CustomGearModelRollRaritiesConfig;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

@Mod.EventBusSubscriber(modid = "vhapi", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CustomGearModelRollRaritiesConfigLoader extends VaultConfigProcessor<CustomGearModelRollRaritiesConfig> {

    public CustomGearModelRollRaritiesConfigLoader() {
        super(new CustomGearModelRollRaritiesConfig(), "gear/custom/model_rolls");
    }


    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(CustomGearModelRollRaritiesConfig config : this.CUSTOM_CONFIGS.values()) {
            if(CustomGearModelRolls.CUSTOM_MODEL_ROLLS_MAP.containsKey(config.itemRegistryName)) {
                for(VaultGearRarity rarity : VaultGearRarity.values()) {
                    if(CustomGearModelRolls.CUSTOM_MODEL_ROLLS_MAP.get(config.itemRegistryName).containsKey(rarity.name())){
                        CustomGearModelRolls.CUSTOM_MODEL_ROLLS_MAP.get(config.itemRegistryName).get(rarity.name()).addAll(config.MODEL_ROLLS.get(rarity));
                    }
                    else {
                        CustomGearModelRolls.CUSTOM_MODEL_ROLLS_MAP.get(config.itemRegistryName).put(rarity.name(), config.MODEL_ROLLS.get(rarity));
                    }
                }
            }
            else {
                if(config.MODEL_ROLLS != null) {
                    CustomGearModelRolls.CUSTOM_MODEL_ROLLS_MAP.put(config.itemRegistryName, config.MODEL_ROLLS);
                }
            }
        }
    }

}
