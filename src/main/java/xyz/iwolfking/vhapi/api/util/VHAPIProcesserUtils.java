package xyz.iwolfking.vhapi.api.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.VHAPI;
import xyz.iwolfking.vhapi.api.LoaderRegistry;
import xyz.iwolfking.vhapi.api.lib.core.processors.IConfigProcessor;
import xyz.iwolfking.vhapi.api.lib.core.readers.CustomVaultConfigReader;
import xyz.iwolfking.vhapi.api.loaders.lib.VHAPIDataLoader;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Path;

public class VHAPIProcesserUtils {

    public static void addManualConfigFile(Path path, ResourceLocation resourceLocation) {
        addManualConfigFile(path.toFile(), resourceLocation);
    }

    public static void addManualConfigFile(File file, ResourceLocation resourceLocation) {
        try (FileReader reader = new FileReader(file)) {
            JsonElement jsonElement = JsonParser.parseReader(reader);
            addToLoader(resourceLocation, jsonElement);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addManualConfigFile(InputStream stream, ResourceLocation resourceLocation) {
        addToLoader(resourceLocation, JsonUtils.parseJsonContentFromStream(stream));
    }


    private static void addToLoader(ResourceLocation location, JsonElement element) {
        if(LoaderRegistry.VHAPI_DATA_LOADER.getIgnoredConfigs().contains(location)) {
            return;
        }

        LoaderRegistry.VHAPI_DATA_LOADER.JSON_DATA.put(location, element);
    }
}
