package xyz.iwolfking.vhapi.api.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import xyz.iwolfking.vhapi.api.lib.core.readers.CustomVaultConfigReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Path;

public class VHAPIProcesserUtils {

    public static void addManualConfigFile(Path path, Type classType) {
        addManualConfigFile(path.toFile(), classType);
    }

    public static void addManualConfigFile(File file, Type classType) {
        try (FileReader reader = new FileReader(file)) {
            JsonElement jsonElement = JsonParser.parseReader(reader);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    }
}
