package xyz.iwolfking.vhapi.api.loaders.lib.core;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import xyz.iwolfking.vhapi.VHAPI;
import xyz.iwolfking.vhapi.api.LoaderRegistry;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.lib.core.readers.GenFileReader;
import xyz.iwolfking.vhapi.api.lib.core.processors.IConfigProcessor;
import xyz.iwolfking.vhapi.api.util.ResourceLocUtils;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = "vhapi", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GenFileProcessor<T> implements IConfigProcessor {
    private final Type instance;
    public final String directory;
    public final Map<ResourceLocation, T> CUSTOM_CONFIGS = new HashMap<>();

    public GenFileProcessor(Type instance, String directory) {
        this.instance = instance;
        this.directory = directory;
        LoaderRegistry.GEN_FILE_LOADERS.put(new ResourceLocation(VHAPI.MODID, this.directory), this);
    }

    public void processMatchingConfigs() {
        GenFileReader<T> configReader = new GenFileReader<>();
        for(ResourceLocation key : LoaderRegistry.VHAPI_DATA_LOADER.JSON_DATA.keySet()) {
            //Attempt to process all configs under the specified directory.
            if(key.getPath().startsWith(directory)) {
                T config = configReader.readCustomConfig(key.getPath(), LoaderRegistry.VHAPI_DATA_LOADER.JSON_DATA.get(key), instance);
                CUSTOM_CONFIGS.put(new ResourceLocation(key.getNamespace(), ResourceLocUtils.removePrefixFromId(directory + "/", key).getPath()), config);
            }
        }
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {

    }
}
