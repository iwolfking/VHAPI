package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.objectives;

import com.google.gson.JsonElement;
import iskallia.vault.config.Config;
import iskallia.vault.config.MonolithConfig;
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
public class MonolithConfigLoader extends VaultConfigDataLoader<MonolithConfig> {
    public static final MonolithConfig instance = new MonolithConfig();

    public MonolithConfigLoader(String namespace) {
        super(instance, "objectives/monolith", new HashMap<>(), namespace);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> dataMap, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        dataMap.forEach((resourceLocation, jsonElement) -> {
            CustomVaultConfigReader<Config> configReader = new CustomVaultConfigReader<>();
            Config config = configReader.readCustomConfig(resourceLocation.getPath(), jsonElement, instance.getClass());
            if(config instanceof MonolithConfig monolithConfig) {
                CUSTOM_CONFIGS.put(new ResourceLocation(getNamespace(), resourceLocation.getPath()), monolithConfig);
            }
        });

        performFinalActions();
    }


    @SubscribeEvent
    public static void afterConfigsLoad(VaultConfigEvent.End event) {
        for(MonolithConfig config : Loaders.MONOLITH_CONFIG_LOADER.CUSTOM_CONFIGS.values()) {
            ModConfigs.MONOLITH = config;
        }
    }


    @SubscribeEvent
    public static void onAddListeners(AddReloadListenerEvent event) {
        event.addListener(Loaders.MONOLITH_CONFIG_LOADER);
    }
}
