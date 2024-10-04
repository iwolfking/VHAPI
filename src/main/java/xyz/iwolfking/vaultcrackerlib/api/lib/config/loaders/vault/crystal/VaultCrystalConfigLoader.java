package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.vault.crystal;

import com.google.gson.JsonElement;
import iskallia.vault.config.VaultCrystalConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.Loaders;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.VaultCrystalConfigAccessor;

import java.util.*;
@Mod.EventBusSubscriber(modid = "vaultcrackerlib", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class VaultCrystalConfigLoader extends VaultConfigDataLoader<VaultCrystalConfig> {
    public static final VaultCrystalConfig instance = new VaultCrystalConfig();

    public VaultCrystalConfigLoader(String namespace) {
        super(instance, "vault/crystal", new HashMap<>(), namespace);
    }

    @SubscribeEvent
    public static void afterConfigsLoad(VaultConfigEvent.End event) {
        System.out.println("READING VAULT CRYSTAL CONFIGS");
        System.out.println(Loaders.VAULT_CRYSTAL_CONFIG_LOADER.CUSTOM_CONFIGS.size());
        for(VaultCrystalConfig config : Loaders.VAULT_CRYSTAL_CONFIG_LOADER.CUSTOM_CONFIGS.values()) {
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


    @SubscribeEvent
    public static void onAddListeners(AddReloadListenerEvent event) {
        event.addListener(Loaders.VAULT_CRYSTAL_CONFIG_LOADER);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> dataMap, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        super.apply(dataMap, resourceManager, profilerFiller);
    }
}
