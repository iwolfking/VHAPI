package xyz.iwolfking.vaultcrackerlib.api.lib.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import iskallia.vault.VaultMod;
import iskallia.vault.config.Config;
import iskallia.vault.config.gear.VaultGearTierConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.io.IOUtils;
import xyz.iwolfking.vaultcrackerlib.VaultCrackerLib;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.MixinConfigAccessor;

import java.io.File;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

public class CustomVaultConfigReader<T extends Config> {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();


    public T readCustomConfig(String name, JsonElement json, T instance) {
        VaultCrackerLib.LOGGER.info("Reading custom config: " + name);
        T readConfig;
        try {
            T config = (T) GSON.fromJson(json, instance.getClass());


            ((MixinConfigAccessor)config).invokeOnLoad(config);

            if (!((MixinConfigAccessor)config).invokeIsValid()) {
                VaultCrackerLib.LOGGER.error("Invalid config {}, using defaults", this);
                ModConfigs.INVALID_CONFIGS.add(((MixinConfigAccessor)config).invokeGetConfigFile().getName() + " - There was an invalid setting in this config.");
                ((MixinConfigAccessor)config).invokeReset();
            }

            ModConfigs.CONFIGS.add(config);
            readConfig = config;
        } catch (JsonSyntaxException e) {
            throw new RuntimeException(e);
        }
        return readConfig;
    }

}
