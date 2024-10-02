package xyz.iwolfking.vaultcrackerlib.api.lib;

import com.google.gson.annotations.Expose;
import iskallia.vault.VaultMod;
import iskallia.vault.config.Config;
import iskallia.vault.init.ModConfigs;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.core.util.JsonUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Set;

public abstract class CustomVaultConfig extends Config  {


    public CustomVaultConfig(String root, String extension) {
        this.root = root;
        this.extension = extension;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    protected void reset() {

    }

    private File getConfigFile() {
        String var10002 = this.root;

        return new File(var10002 + this.getName() + this.extension);
    }



    public <T extends CustomVaultConfig> T readCustomConfig(MinecraftServer server) {
        VaultMod.LOGGER.info("Reading custom config: " + this.getName());
        CustomVaultConfig readConfig;
        label39:
        {
            try {
                ResourceManager manager = server.getResourceManager();
                if (manager.hasResource(VaultMod.id("test.json"))) {
                    Resource test = null;
                    test = manager.getResource(VaultMod.id("test.json"));
                    String configData = IOUtils.toString(test.getInputStream(), Charset.defaultCharset());
                    CustomVaultConfig config = GSON.fromJson(configData, this.getClass());

                    config.onLoad(this);

                    if (!config.isValid()) {
                        VaultMod.LOGGER.error("Invalid config {}, using defaults", this);
                        ModConfigs.INVALID_CONFIGS.add(this.getConfigFile().getName() + " - There was an invalid setting in this config.");
                        config.reset();
                    }
                    ModConfigs.CONFIGS.add(config);
                    readConfig = config;
                    break label39;

                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
        return null;
    }



}
