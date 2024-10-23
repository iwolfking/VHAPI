package xyz.iwolfking.vhapi.api.lib.loaders;

import iskallia.vault.config.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import xyz.iwolfking.vhapi.api.LoaderRegistry;
import xyz.iwolfking.vhapi.api.lib.config.CustomVaultConfigReader;
import xyz.iwolfking.vhapi.api.lib.loaders.core.processors.IConfigProcessor;
import xyz.iwolfking.vhapi.api.util.ResourceLocUtils;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = "vhapi", bus = Mod.EventBusSubscriber.Bus.FORGE)
public abstract class VaultConfigDataLoaderRF<T extends Config> implements IConfigProcessor {
    private final T instance;
    public final String directory;
    private final String namespace;
    public final Map<ResourceLocation, T> CUSTOM_CONFIGS = new HashMap<>();

    public VaultConfigDataLoaderRF(T instance, String directory, String namespace) {
        this.instance = instance;
        this.directory = directory;
        this.namespace = namespace;
    }

    public void processMatchingConfigs() {
        CustomVaultConfigReader<T> configReader = new CustomVaultConfigReader<>();
        for(ResourceLocation key : LoaderRegistry.VHAPI_DATA_LOADER.JSON_DATA.keySet()) {
            //Attempt to process all configs under the specified directory.
            if(key.getPath().startsWith(directory)) {
                T config = configReader.readCustomConfig(key.getPath(), LoaderRegistry.VHAPI_DATA_LOADER.JSON_DATA.get(key), instance.getClass());
                CUSTOM_CONFIGS.put(new ResourceLocation(namespace, ResourceLocUtils.removePrefixFromId(directory + "/", key).getPath()), config);
            }
        }
    }
}
