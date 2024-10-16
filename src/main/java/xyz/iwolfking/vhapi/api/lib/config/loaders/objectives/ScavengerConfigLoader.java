package xyz.iwolfking.vhapi.api.lib.config.loaders.objectives;

import com.google.gson.JsonElement;
import iskallia.vault.config.Config;
import iskallia.vault.config.ScavengerConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.fml.common.Mod;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.lib.config.CustomVaultConfigReader;
import xyz.iwolfking.vhapi.api.lib.loaders.VaultConfigDataLoader;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = "vhapi", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ScavengerConfigLoader extends VaultConfigDataLoader<ScavengerConfig> {
    public static final ScavengerConfig instance = new ScavengerConfig();

    public ScavengerConfigLoader(String namespace) {
        super(new ScavengerConfig(), "objectives/scavenger", new HashMap<>(), namespace);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> dataMap, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        dataMap.forEach((resourceLocation, jsonElement) -> {
            CustomVaultConfigReader<Config> configReader = new CustomVaultConfigReader<>();
            Config config = configReader.readCustomConfig(resourceLocation.getPath(), jsonElement, ScavengerConfig.class);
            if(config instanceof ScavengerConfig scavConfig) {
                CUSTOM_CONFIGS.put(new ResourceLocation(getNamespace(), resourceLocation.getPath()), scavConfig);
            }
        });

        performFinalActions();
    }


    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ScavengerConfig config : this.CUSTOM_CONFIGS.values()) {
            ModConfigs.SCAVENGER = config;
        }
    }

}
