package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.objectives;

import com.google.gson.JsonElement;
import iskallia.vault.config.Config;
import iskallia.vault.config.ScavengerConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.CustomVaultConfigReader;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.Loaders;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = "vaultcrackerlib", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ScavengerConfigLoader extends VaultConfigDataLoader<ScavengerConfig> {
    public static final ScavengerConfig instance = new ScavengerConfig();

    public ScavengerConfigLoader(String namespace) {
        super(instance, "objectives/scavenger", new HashMap<>(), namespace);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> dataMap, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        dataMap.forEach((resourceLocation, jsonElement) -> {
            CustomVaultConfigReader<Config> configReader = new CustomVaultConfigReader<>();
            Config config = configReader.readCustomConfig(resourceLocation.getPath(), jsonElement, instance.getClass());
            if(config instanceof ScavengerConfig scavConfig) {
                CUSTOM_CONFIGS.put(new ResourceLocation(getNamespace(), resourceLocation.getPath()), scavConfig);
            }
        });

        performFinalActions();
    }


    @SubscribeEvent
    public static void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ScavengerConfig config : Loaders.SCAVENGER_CONFIG_LOADER.CUSTOM_CONFIGS.values()) {
            ModConfigs.SCAVENGER = config;
        }
    }


    @SubscribeEvent
    public static void onAddListeners(AddReloadListenerEvent event) {
        event.addListener(Loaders.SCAVENGER_CONFIG_LOADER);
    }

}
