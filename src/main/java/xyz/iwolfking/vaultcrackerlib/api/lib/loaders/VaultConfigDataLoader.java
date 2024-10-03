package xyz.iwolfking.vaultcrackerlib.api.lib.loaders;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import iskallia.vault.VaultMod;
import iskallia.vault.config.Config;
import iskallia.vault.config.gear.VaultGearTierConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.apache.http.cookie.SM;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.CustomVaultConfigReader;

import java.util.HashMap;
import java.util.Map;

public class VaultConfigDataLoader<T extends Config> extends SimpleJsonResourceReloadListener {
    private static final Gson GSON_INSTANCE = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private final T instance;


    private boolean isPatched = false;


    private final String namespace;

    public Map<ResourceLocation, T> CUSTOM_CONFIGS;

    public VaultConfigDataLoader(T instance, String directory, Map<ResourceLocation, T> configMap, String namespace) {
        super(GSON_INSTANCE, directory);
        this.instance = instance;
        this.CUSTOM_CONFIGS = configMap;
        this.namespace = namespace;
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> dataMap, ResourceManager resourceManager, ProfilerFiller profilerFiller) {

        dataMap.forEach((resourceLocation, jsonElement) -> {
            CustomVaultConfigReader<T> configReader = new CustomVaultConfigReader<>();
            T config = configReader.readCustomConfig(resourceLocation.getPath(), jsonElement, instance.getClass());
            CUSTOM_CONFIGS.put(new ResourceLocation(namespace, resourceLocation.getPath()), config);
        });

        performFinalActions();
    }

    protected void performFinalActions() {

    }


    public String getNamespace() {
        return namespace;
    }

    public boolean isPatched() {
        return isPatched;
    }

    public void setPatched(boolean patched) {
        isPatched = patched;
    }

}
