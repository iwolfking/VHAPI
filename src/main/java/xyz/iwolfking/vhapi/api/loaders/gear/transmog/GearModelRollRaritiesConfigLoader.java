package xyz.iwolfking.vhapi.api.loaders.gear.transmog;

import iskallia.vault.config.GearModelRollRaritiesConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraftforge.fml.common.Mod;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.GearModelRollRaritiesAccessor;

@Mod.EventBusSubscriber(modid = "vhapi", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GearModelRollRaritiesConfigLoader extends VaultConfigProcessor<GearModelRollRaritiesConfig> {

    public GearModelRollRaritiesConfigLoader() {
        super(new GearModelRollRaritiesConfig(), "gear/model_rolls");
    }


    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(GearModelRollRaritiesConfig config : this.CUSTOM_CONFIGS.values()) {
            if(((GearModelRollRaritiesAccessor)config).getArmorModelRolls() != null) {
                for(String key : ((GearModelRollRaritiesAccessor)ModConfigs.GEAR_MODEL_ROLL_RARITIES).getArmorModelRolls().keySet()) {
                    ((GearModelRollRaritiesAccessor)ModConfigs.GEAR_MODEL_ROLL_RARITIES).getArmorModelRolls().get(key).addAll(((GearModelRollRaritiesAccessor) config).getArmorModelRolls().get(key));
                }
            }
            if(((GearModelRollRaritiesAccessor)config).getAxeModelRolls() != null) {
                for(String key : ((GearModelRollRaritiesAccessor)ModConfigs.GEAR_MODEL_ROLL_RARITIES).getArmorModelRolls().keySet()) {
                    ((GearModelRollRaritiesAccessor)ModConfigs.GEAR_MODEL_ROLL_RARITIES).getAxeModelRolls().get(key).addAll(((GearModelRollRaritiesAccessor) config).getAxeModelRolls().get(key));
                }
            }
            if(((GearModelRollRaritiesAccessor)config).getSwordModelRolls() != null) {
                for(String key : ((GearModelRollRaritiesAccessor)ModConfigs.GEAR_MODEL_ROLL_RARITIES).getSwordModelRolls().keySet()) {
                    ((GearModelRollRaritiesAccessor)ModConfigs.GEAR_MODEL_ROLL_RARITIES).getSwordModelRolls().get(key).addAll(((GearModelRollRaritiesAccessor) config).getSwordModelRolls().get(key));
                }
            }
            if(((GearModelRollRaritiesAccessor)config).getShieldModelRolls() != null) {
                for(String key : ((GearModelRollRaritiesAccessor)ModConfigs.GEAR_MODEL_ROLL_RARITIES).getArmorModelRolls().keySet()) {
                    ((GearModelRollRaritiesAccessor)ModConfigs.GEAR_MODEL_ROLL_RARITIES).getShieldModelRolls().get(key).addAll(((GearModelRollRaritiesAccessor) config).getShieldModelRolls().get(key));
                }
            }
            if(((GearModelRollRaritiesAccessor)config).getWandModelRolls() != null) {
                for(String key : ((GearModelRollRaritiesAccessor)ModConfigs.GEAR_MODEL_ROLL_RARITIES).getArmorModelRolls().keySet()) {
                    ((GearModelRollRaritiesAccessor)ModConfigs.GEAR_MODEL_ROLL_RARITIES).getWandModelRolls().get(key).addAll(((GearModelRollRaritiesAccessor) config).getWandModelRolls().get(key));
                }
            }
            if(((GearModelRollRaritiesAccessor)config).getFocusModelRolls() != null) {
                for(String key : ((GearModelRollRaritiesAccessor)ModConfigs.GEAR_MODEL_ROLL_RARITIES).getArmorModelRolls().keySet()) {
                    ((GearModelRollRaritiesAccessor)ModConfigs.GEAR_MODEL_ROLL_RARITIES).getFocusModelRolls().get(key).addAll(((GearModelRollRaritiesAccessor) config).getFocusModelRolls().get(key));
                }
            }
        }
    }

}
