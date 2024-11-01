package xyz.iwolfking.vhapi.api.loaders.lib;

import com.google.gson.JsonElement;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import xyz.iwolfking.vhapi.VHAPI;
import xyz.iwolfking.vhapi.api.LoaderRegistry;
import xyz.iwolfking.vhapi.api.data.core.ConfigData;
import xyz.iwolfking.vhapi.api.lib.core.processors.IConfigProcessor;
import xyz.iwolfking.vhapi.api.lib.core.processors.IPreProcessor;
import xyz.iwolfking.vhapi.api.util.vhapi.VHAPILoggerUtils;
import xyz.iwolfking.vhapi.config.VHAPIConfig;
import xyz.iwolfking.vhapi.networking.util.StringCompressor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class VHAPIDataLoader extends SimpleJsonResourceReloadListener {
    private boolean isInitialized = false;
    private final String namespace = VHAPI.MODID;
    public Map<ResourceLocation, JsonElement> JSON_DATA = new HashMap<>();
    private final Set<ResourceLocation> CONFIGS_TO_IGNORE = new HashSet<>();

    public VHAPIDataLoader() {
        super(ConfigData.CONFIG_LOADER_GSON, "vault_configs");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> dataMap, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        profilerFiller.startTick();

        dataMap.forEach((resourceLocation, jsonElement) -> {
            if(!getIgnoredConfigs().contains(resourceLocation)) {
                JSON_DATA.put(new ResourceLocation(namespace, resourceLocation.getPath()), jsonElement);
            }
        });

        profilerFiller.push("process_vault_configs");

        gatherConfigsToProcessors();

        profilerFiller.pop();

        profilerFiller.endTick();
        VHAPILoggerUtils.info("Finished processing " + dataMap.size() + " custom vault configs.");


    }

    public void addIgnoredConfig(ResourceLocation configLocation) {
        CONFIGS_TO_IGNORE.add(configLocation);
    }

    public Set<ResourceLocation> getIgnoredConfigs() {
        Set<ResourceLocation> locations = CONFIGS_TO_IGNORE;
        locations.addAll(VHAPIConfig.COMMON.getIgnoredConfigs());
        return locations;
    }

    public Map<ResourceLocation, byte[]> getCompressedConfigMap() {
        Map<ResourceLocation, byte[]> returnMap = new HashMap<>();

        for(ResourceLocation loc : JSON_DATA.keySet()) {
            if(getIgnoredConfigs().contains(loc)) {
                continue;
            }
            returnMap.put(loc, StringCompressor.compress(JSON_DATA.get(loc).toString()));
        }

        return returnMap;
    }

    public void gatherConfigsToProcessors() {
        if(!isInitialized) {
            isInitialized = true;
            LoaderRegistry.initProcessors();
        }

        for(int i = 0; i < LoaderRegistry.CONFIG_PROCESSORS.size(); i++) {

            IConfigProcessor configProcessor = LoaderRegistry.CONFIG_PROCESSORS.get(i);
            configProcessor.processMatchingConfigs();

            if (configProcessor instanceof IPreProcessor preConfigProcessor) {
                preConfigProcessor.preProcessStep();
            }

        }

        ModConfigs.register();

    }
}
