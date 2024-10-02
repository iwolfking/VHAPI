package xyz.iwolfking.vaultcrackerlib.api.loaders;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import iskallia.vault.VaultMod;
import iskallia.vault.config.gear.VaultGearTierConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.gear.CustomVaultGearToolTierConfig;

import java.util.HashMap;
import java.util.Map;

public class CustomVaultGearLoader extends SimpleJsonResourceReloadListener {

    private static final Gson GSON_INSTANCE = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public static Map<ResourceLocation, CustomVaultGearToolTierConfig> CUSTOM_GEAR_CONFIGS = new HashMap<>();
    public CustomVaultGearLoader() {

        super(GSON_INSTANCE, "gear_modifiers");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> dataMap, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        dataMap.forEach(((resourceLocation, jsonElement) -> {
            System.out.println("Data loader picked up : " + resourceLocation.getPath());
            CustomVaultGearToolTierConfig config = new CustomVaultGearToolTierConfig(VaultMod.id(resourceLocation.getPath())).readCustomConfig(jsonElement);
            System.out.println(config.getName());
            CUSTOM_GEAR_CONFIGS.put(VaultMod.id(resourceLocation.getPath()), config);
        }));

        VaultGearTierConfig.registerConfigs();

    }
}
