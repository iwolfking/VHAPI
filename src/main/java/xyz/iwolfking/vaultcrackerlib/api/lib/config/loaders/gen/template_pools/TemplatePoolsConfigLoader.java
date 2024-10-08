package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gen.template_pools;

import com.google.gson.JsonElement;
import iskallia.vault.config.core.TemplatePoolsConfig;
import iskallia.vault.core.Version;
import iskallia.vault.core.data.key.TemplatePoolKey;
import iskallia.vault.core.vault.VaultRegistry;
import iskallia.vault.core.world.template.data.IndirectTemplateEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.CustomVaultConfigReader;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.KeyRegistryAccessor;
import java.util.HashMap;
import java.util.Map;

public class TemplatePoolsConfigLoader extends VaultConfigDataLoader<TemplatePoolsConfig> {
    public TemplatePoolsConfigLoader(String namespace) {
        super(new TemplatePoolsConfig(), "template_pools", new HashMap<>(), namespace);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> dataMap, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        ((KeyRegistryAccessor) VaultRegistry.TEMPLATE_POOL).setLocked(false);
        dataMap.forEach((resourceLocation, jsonElement) -> {
            if(!getIgnoredConfigs().contains(resourceLocation)) {
                CustomVaultConfigReader<TemplatePoolsConfig> configReader = new CustomVaultConfigReader<>();
                TemplatePoolsConfig config = configReader.readCustomConfig(resourceLocation.getPath(), jsonElement, TemplatePoolsConfig.class);
                for(TemplatePoolKey key : config.toRegistry().getKeys()) {
                    VaultRegistry.TEMPLATE_POOL.register(key.with(Version.latest(), key.get(Version.v1_0)));
                }
                CUSTOM_CONFIGS.put(new ResourceLocation(getNamespace(), resourceLocation.getPath()), config);
            }
        });
        ((KeyRegistryAccessor)VaultRegistry.TEMPLATE_POOL).setLocked(true);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        ((KeyRegistryAccessor) VaultRegistry.TEMPLATE_POOL).setLocked(false);
        for(TemplatePoolsConfig config : this.CUSTOM_CONFIGS.values()) {
            for(TemplatePoolKey key : config.toRegistry().getKeys()) {
                VaultRegistry.TEMPLATE_POOL.register(key.with(Version.latest(), key.get(Version.v1_0)));
            }
        }
        ((KeyRegistryAccessor)VaultRegistry.TEMPLATE_POOL).setLocked(true);
    }
}
