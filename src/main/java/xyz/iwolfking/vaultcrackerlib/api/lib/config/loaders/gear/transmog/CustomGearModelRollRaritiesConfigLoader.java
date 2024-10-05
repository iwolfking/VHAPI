package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear.transmog;

import iskallia.vault.VaultMod;
import iskallia.vault.config.GearModelRollRaritiesConfig;
import iskallia.vault.config.gear.VaultGearTierConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.spongepowered.asm.mixin.Mixin;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.Loaders;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.GearModelRollRaritiesAccessor;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = "vaultcrackerlib", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CustomGearModelRollRaritiesConfigLoader extends VaultConfigDataLoader<GearModelRollRaritiesConfig> {
    public static final GearModelRollRaritiesConfig instance = new GearModelRollRaritiesConfig();

    public CustomGearModelRollRaritiesConfigLoader(String namespace) {
        super(instance, "gear/model_rolls", new HashMap<>(), namespace);
    }

    @SubscribeEvent
    public static void onAddListeners(AddReloadListenerEvent event) {
        event.addListener(Loaders.GEAR_MODEL_ROLL_RARITIES_CONFIG_LOADER);
    }

    @SubscribeEvent
    public static void afterConfigsLoad(VaultConfigEvent.End event) {
        for(GearModelRollRaritiesConfig config : Loaders.GEAR_MODEL_ROLL_RARITIES_CONFIG_LOADER.CUSTOM_CONFIGS.values()) {
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
