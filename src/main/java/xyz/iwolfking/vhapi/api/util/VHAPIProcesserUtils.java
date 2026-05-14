package xyz.iwolfking.vhapi.api.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.LoaderRegistry;
import java.io.*;
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

    public static void addIgnoredConfig(ResourceLocation resourceLocation) {
        LoaderRegistry.VHAPI_DATA_LOADER.addIgnoredConfig(resourceLocation);
    }


    private static void addToLoader(ResourceLocation location, JsonElement element) {
        if(LoaderRegistry.VHAPI_DATA_LOADER.getIgnoredConfigs().contains(location)) {
            return;
        }

        LoaderRegistry.VHAPI_DATA_LOADER.MANUAL_JSON_DATA.put(location, element);
    }
}
