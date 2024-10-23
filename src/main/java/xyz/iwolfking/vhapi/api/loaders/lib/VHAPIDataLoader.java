package xyz.iwolfking.vhapi.api.loaders.lib;

import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.common.MinecraftForge;
import xyz.iwolfking.vhapi.VHAPI;
import xyz.iwolfking.vhapi.api.LoaderRegistry;
import xyz.iwolfking.vhapi.api.data.core.ConfigData;
import xyz.iwolfking.vhapi.api.lib.core.processors.IConfigProcessor;
import xyz.iwolfking.vhapi.api.lib.core.processors.IPreConfigProcessor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class VHAPIDataLoader extends SimpleJsonResourceReloadListener {

    private final String namespace = VHAPI.MODID;
    public Map<ResourceLocation, JsonElement> JSON_DATA = new HashMap<>();
    private Set<ResourceLocation> CONFIGS_TO_IGNORE = new HashSet<>();

    public VHAPIDataLoader() {
        super(ConfigData.CONFIG_LOADER_GSON, "vault_configs");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> dataMap, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        dataMap.forEach((resourceLocation, jsonElement) -> {
            if(!CONFIGS_TO_IGNORE.contains(resourceLocation)) {
                JSON_DATA.put(new ResourceLocation(namespace, resourceLocation.getPath()), jsonElement);
            }
        });


        for(int i = 0; i < LoaderRegistry.CONFIG_PROCESSORS.size(); i++) {
            IConfigProcessor configProcessor = LoaderRegistry.CONFIG_PROCESSORS.get(i);
            configProcessor.processMatchingConfigs();

            if(configProcessor instanceof IPreConfigProcessor preConfigProcessor) {
                preConfigProcessor.preProcessStep();
            }


            MinecraftForge.EVENT_BUS.addListener(configProcessor::afterConfigsLoad);

        }



    }

    public void addIgnoredConfig(ResourceLocation configLocation) {
        CONFIGS_TO_IGNORE.add(configLocation);
    }

    public Set<ResourceLocation> getIgnoredConfigs() {
        return CONFIGS_TO_IGNORE;
    }
}
