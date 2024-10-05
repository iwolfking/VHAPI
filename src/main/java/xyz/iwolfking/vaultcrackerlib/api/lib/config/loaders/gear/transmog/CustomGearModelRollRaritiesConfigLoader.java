package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear.transmog;

import iskallia.vault.config.GearModelRollRaritiesConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraftforge.fml.common.Mod;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.GearModelRollRaritiesAccessor;

import java.util.HashMap;

@Mod.EventBusSubscriber(modid = "vaultcrackerlib", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CustomGearModelRollRaritiesConfigLoader extends VaultConfigDataLoader<GearModelRollRaritiesConfig> {
    public static final GearModelRollRaritiesConfig instance = new GearModelRollRaritiesConfig();

    public CustomGearModelRollRaritiesConfigLoader(String namespace) {
        super(instance, "gear/model_rolls", new HashMap<>(), namespace);
    }


    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(GearModelRollRaritiesConfig config : this.CUSTOM_CONFIGS.values()) {
            if(((GearModelRollRaritiesAccessor)config).getArmorModelRolls() != null) {
                ((GearModelRollRaritiesAccessor)ModConfigs.GEAR_MODEL_ROLL_RARITIES).getArmorModelRolls().putAll(((GearModelRollRaritiesAccessor) config).getArmorModelRolls());
            }
            if(((GearModelRollRaritiesAccessor)config).getAxeModelRolls() != null) {
                ((GearModelRollRaritiesAccessor)ModConfigs.GEAR_MODEL_ROLL_RARITIES).getAxeModelRolls().putAll(((GearModelRollRaritiesAccessor) config).getAxeModelRolls());
            }

            if(((GearModelRollRaritiesAccessor)config).getFocusModelRolls() != null) {
                ((GearModelRollRaritiesAccessor)ModConfigs.GEAR_MODEL_ROLL_RARITIES).getFocusModelRolls().putAll(((GearModelRollRaritiesAccessor) config).getFocusModelRolls());
            }

            if(((GearModelRollRaritiesAccessor)config).getShieldModelRolls() != null) {
                ((GearModelRollRaritiesAccessor)ModConfigs.GEAR_MODEL_ROLL_RARITIES).getShieldModelRolls().putAll(((GearModelRollRaritiesAccessor) config).getShieldModelRolls());
            }

            if(((GearModelRollRaritiesAccessor)config).getSwordModelRolls() != null) {
                ((GearModelRollRaritiesAccessor)ModConfigs.GEAR_MODEL_ROLL_RARITIES).getSwordModelRolls().putAll(((GearModelRollRaritiesAccessor) config).getSwordModelRolls());
            }

            if(((GearModelRollRaritiesAccessor)config).getWandModelRolls() != null) {
                ((GearModelRollRaritiesAccessor)ModConfigs.GEAR_MODEL_ROLL_RARITIES).getWandModelRolls().putAll(((GearModelRollRaritiesAccessor) config).getWandModelRolls());
            }
        }
    }

}
