package xyz.iwolfking.vhapi.api.registry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import iskallia.vault.config.VaultModifiersConfig;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.VHAPI;
import xyz.iwolfking.vhapi.api.lib.core.readers.CustomVaultConfigReader;
import xyz.iwolfking.vhapi.api.loaders.Processors;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class VaultModifierConfigRegistry {
    private static JsonElement createModifierConfigJson(File file) {
        try (FileReader reader = new FileReader(file)) {
            JsonElement jsonElement = JsonParser.parseReader(reader);
            return jsonElement;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new JsonObject();
    }

    public static void addVaultModifiersFromFile(ResourceLocation key, File file) {
        CustomVaultConfigReader<VaultModifiersConfig> vaultModifiersConfigCustomVaultConfigReader = new CustomVaultConfigReader<>();
        try {
            VaultModifiersConfig config = vaultModifiersConfigCustomVaultConfigReader.readCustomConfig(key.getPath(), createModifierConfigJson(file), VaultModifiersConfig.class);
            Processors.VaultModifierConfigProcessors.VAULT_MODIFIER_CONFIG_LOADER.addManualConfig(key, config);
        }
        catch (Exception e) {
            VHAPI.LOGGER.error("Failed to load " + key + " config manually!");
            e.printStackTrace();
        }
    }


}
