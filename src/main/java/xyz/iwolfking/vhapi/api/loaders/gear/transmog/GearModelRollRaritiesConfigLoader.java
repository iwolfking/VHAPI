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
                for(String key : ((GearModelRollRaritiesAccessor)config).getArmorModelRolls().keySet()) {
                    if( ((GearModelRollRaritiesAccessor)ModConfigs.GEAR_MODEL_ROLL_RARITIES).getArmorModelRolls().containsKey(key)) {
                        ((GearModelRollRaritiesAccessor)ModConfigs.GEAR_MODEL_ROLL_RARITIES).getArmorModelRolls().get(key).addAll(((GearModelRollRaritiesAccessor) config).getArmorModelRolls().get(key));
                    }
                    else {
                        ((GearModelRollRaritiesAccessor)ModConfigs.GEAR_MODEL_ROLL_RARITIES).getArmorModelRolls().put(key, ((GearModelRollRaritiesAccessor) config).getArmorModelRolls().get(key));
                    }
                }
            }
            if(((GearModelRollRaritiesAccessor)config).getAxeModelRolls() != null) {
                for(String key : ((GearModelRollRaritiesAccessor)config).getAxeModelRolls().keySet()) {
                    if( ((GearModelRollRaritiesAccessor)ModConfigs.GEAR_MODEL_ROLL_RARITIES).getAxeModelRolls().containsKey(key)) {
                        ((GearModelRollRaritiesAccessor)ModConfigs.GEAR_MODEL_ROLL_RARITIES).getAxeModelRolls().get(key).addAll(((GearModelRollRaritiesAccessor) config).getAxeModelRolls().get(key));
                    }
                    else {
                        ((GearModelRollRaritiesAccessor)ModConfigs.GEAR_MODEL_ROLL_RARITIES).getAxeModelRolls().put(key, ((GearModelRollRaritiesAccessor) config).getAxeModelRolls().get(key));
                    }
                }
            }
            if(((GearModelRollRaritiesAccessor)config).getSwordModelRolls() != null) {
                for(String key : ((GearModelRollRaritiesAccessor)config).getSwordModelRolls().keySet()) {
                        if( ((GearModelRollRaritiesAccessor)ModConfigs.GEAR_MODEL_ROLL_RARITIES).getSwordModelRolls().containsKey(key)) {
                            ((GearModelRollRaritiesAccessor)ModConfigs.GEAR_MODEL_ROLL_RARITIES).getSwordModelRolls().get(key).addAll(((GearModelRollRaritiesAccessor) config).getSwordModelRolls().get(key));
                        }
                        else {
                            ((GearModelRollRaritiesAccessor)ModConfigs.GEAR_MODEL_ROLL_RARITIES).getSwordModelRolls().put(key, ((GearModelRollRaritiesAccessor) config).getSwordModelRolls().get(key));
                        }
                }
            }
            if(((GearModelRollRaritiesAccessor)config).getShieldModelRolls() != null) {
                for(String key : ((GearModelRollRaritiesAccessor)config).getShieldModelRolls().keySet()) {
                    if( ((GearModelRollRaritiesAccessor)ModConfigs.GEAR_MODEL_ROLL_RARITIES).getShieldModelRolls().containsKey(key)) {
                        ((GearModelRollRaritiesAccessor)ModConfigs.GEAR_MODEL_ROLL_RARITIES).getShieldModelRolls().get(key).addAll(((GearModelRollRaritiesAccessor) config).getShieldModelRolls().get(key));
                    }
                    else {
                        ((GearModelRollRaritiesAccessor)ModConfigs.GEAR_MODEL_ROLL_RARITIES).getShieldModelRolls().put(key, ((GearModelRollRaritiesAccessor) config).getShieldModelRolls().get(key));
                    }
                }
            }
            if(((GearModelRollRaritiesAccessor)config).getWandModelRolls() != null) {
                for(String key : ((GearModelRollRaritiesAccessor)config).getWandModelRolls().keySet()) {
                    if( ((GearModelRollRaritiesAccessor)ModConfigs.GEAR_MODEL_ROLL_RARITIES).getWandModelRolls().containsKey(key)) {
                        ((GearModelRollRaritiesAccessor)ModConfigs.GEAR_MODEL_ROLL_RARITIES).getWandModelRolls().get(key).addAll(((GearModelRollRaritiesAccessor) config).getWandModelRolls().get(key));
                    }
                    else {
                        ((GearModelRollRaritiesAccessor)ModConfigs.GEAR_MODEL_ROLL_RARITIES).getWandModelRolls().put(key, ((GearModelRollRaritiesAccessor) config).getWandModelRolls().get(key));
                    }
                }
            }
            if(((GearModelRollRaritiesAccessor)config).getFocusModelRolls() != null) {
                for(String key : ((GearModelRollRaritiesAccessor)config).getFocusModelRolls().keySet()) {
                    if( ((GearModelRollRaritiesAccessor)ModConfigs.GEAR_MODEL_ROLL_RARITIES).getFocusModelRolls().containsKey(key)) {
                        ((GearModelRollRaritiesAccessor)ModConfigs.GEAR_MODEL_ROLL_RARITIES).getFocusModelRolls().get(key).addAll(((GearModelRollRaritiesAccessor) config).getFocusModelRolls().get(key));
                    }
                    else {
                        ((GearModelRollRaritiesAccessor)ModConfigs.GEAR_MODEL_ROLL_RARITIES).getFocusModelRolls().put(key, ((GearModelRollRaritiesAccessor) config).getFocusModelRolls().get(key));
                    }
                }
            }
        }
    }

}
