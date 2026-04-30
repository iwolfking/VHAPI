package xyz.iwolfking.vhapi.api.lib.core.readers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import iskallia.vault.VaultMod;
import iskallia.vault.config.*;
import iskallia.vault.config.adapter.*;
import iskallia.vault.config.gear.VaultGearTierConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.VHAPI;
import xyz.iwolfking.vhapi.api.data.core.ConfigData;
import xyz.iwolfking.vhapi.api.util.ResourceLocUtils;
import xyz.iwolfking.vhapi.api.util.vhapi.VHAPILoggerUtils;
import xyz.iwolfking.vhapi.mixin.accessors.MixinConfigAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.VaultGearTierConfigAccessor;

import java.lang.reflect.Type;

public class CustomVaultConfigReader<T extends Config> {

    public static final Gson GSON = ConfigData.CONFIG_LOADER_GSON;

    /**
     *
     * @param name The file name of the datapack file.
     * @param json The JSON data collected from the datapack loader.
     * @param instance The type of class for GSON to use as a reference.
     * @return An instance of the instance class with the data from json.
     */
    public T readCustomConfig(String name, JsonElement json, Type instance) {
        VHAPILoggerUtils.debug(String.format("Reading custom %s config: %s", instance.getTypeName().toLowerCase(), name));
        T readConfig;
        try {
            T config = GSON.fromJson(json, instance);

            if(config instanceof VaultGearTierConfig tierConfig) {
                if(((VaultGearTierConfigAccessor)tierConfig).getKey() == null) {
                    ((VaultGearTierConfigAccessor) tierConfig).setKey(VaultMod.id(name));
                }
            }


            ((MixinConfigAccessor)config).invokeOnLoad(config);

            if (!((MixinConfigAccessor)config).invokeIsValid()) {
                VHAPI.LOGGER.error("Invalid config {}, using defaults", this);
                ModConfigs.INVALID_CONFIGS.add(((MixinConfigAccessor)config).invokeGetConfigFile().getName() + " - There was an invalid setting in this config.");
                ((MixinConfigAccessor)config).invokeReset();
            }

            readConfig = config;
        } catch (Exception e) {
            VHAPILoggerUtils.info("Failed to read " + name);
            return null;
        }
        return readConfig;
    }

}
