package xyz.iwolfking.vaultcrackerlib.api.lib.config.gear;

import com.google.gson.JsonElement;
import iskallia.vault.VaultMod;
import iskallia.vault.config.gear.VaultGearTierConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.apache.commons.io.IOUtils;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.CustomVaultConfig;

import java.io.File;
import java.nio.charset.Charset;

public class CustomVaultGearToolTierConfig extends VaultGearTierConfig {

    public CustomVaultGearToolTierConfig(ResourceLocation key) {
        super(key);
    }

    private File getConfigFile() {
        return new File(this.getName() + this.extension);
    }

    public <T extends CustomVaultGearToolTierConfig> T readCustomConfig(MinecraftServer server) {
        VaultMod.LOGGER.info("Reading custom vault gear config: " + this.getName());
        CustomVaultGearToolTierConfig readConfig;
        label39:
        {
            try {
                ResourceManager manager = server.getResourceManager();
                if (manager.hasResource(VaultMod.id(this.getName()))) {
                    Resource test = null;
                    test = manager.getResource(VaultMod.id(this.getName()));
                    String configData = IOUtils.toString(test.getInputStream(), Charset.defaultCharset());
                    CustomVaultGearToolTierConfig config = GSON.fromJson(configData, this.getClass());

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

    public CustomVaultGearToolTierConfig readCustomConfig(JsonElement json) {
        VaultMod.LOGGER.info("Reading custom vault gear config: " + this.getName());
        CustomVaultGearToolTierConfig readConfig = null;
        label39:
        {
            try {
                    CustomVaultGearToolTierConfig config = GSON.fromJson(json, this.getClass());

                    config.onLoad(this);

                    if (!config.isValid()) {
                        VaultMod.LOGGER.error("Invalid config {}, using defaults", this);
                        ModConfigs.INVALID_CONFIGS.add(this.getConfigFile().getName() + " - There was an invalid setting in this config.");
                        config.reset();
                    }
                    ModConfigs.CONFIGS.add(config);
                    readConfig = config;
                    break label39;

            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
        return readConfig;
    }
}
