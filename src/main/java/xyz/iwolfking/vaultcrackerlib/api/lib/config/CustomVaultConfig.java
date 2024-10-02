package xyz.iwolfking.vaultcrackerlib.api.lib.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import iskallia.vault.VaultMod;
import iskallia.vault.config.Config;
import iskallia.vault.core.vault.Vault;
import iskallia.vault.init.ModConfigs;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.apache.commons.io.IOUtils;
import xyz.iwolfking.vaultcrackerlib.VaultCrackerLib;

import java.io.File;
import java.nio.charset.Charset;

public abstract class CustomVaultConfig extends Config  {


    public CustomVaultConfig() {
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    protected void reset() {

    }

    private File getConfigFile() {
        return new File(this.getName() + this.extension);
    }



    public CustomVaultConfig readCustomConfig(JsonElement json) {
        VaultCrackerLib.LOGGER.info("Reading custom config: " + this.getName());
        CustomVaultConfig readConfig;
        try {
            CustomVaultConfig config = GSON.fromJson(json, this.getClass());

            config.onLoad(this);

            if (!config.isValid()) {
                VaultCrackerLib.LOGGER.error("Invalid config {}, using defaults", this);
                ModConfigs.INVALID_CONFIGS.add(this.getConfigFile().getName() + " - There was an invalid setting in this config.");
                config.reset();
            }

            ModConfigs.CONFIGS.add(config);
            readConfig = config;
        } catch (JsonSyntaxException e) {
            throw new RuntimeException(e);
        }

        return readConfig;
    }


}
