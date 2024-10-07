package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gen.theme;

import iskallia.vault.config.core.ThemesConfig;
import iskallia.vault.core.data.key.ThemeKey;
import iskallia.vault.core.vault.VaultRegistry;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.KeyRegistryAccessor;

import java.util.HashMap;
import java.util.Map;

public class ThemeConfigLoader extends VaultConfigDataLoader<ThemesConfig> {
    public ThemeConfigLoader(String namespace) {
        super(new ThemesConfig(), "themes", new HashMap<>(), namespace);
    }

    public ThemeConfigLoader(ThemesConfig instance, String directory, Map<ResourceLocation, ThemesConfig> configMap, String namespace) {
        super(instance, directory, configMap, namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        ((KeyRegistryAccessor)VaultRegistry.THEME).setLocked(false);
        for(ThemesConfig config : this.CUSTOM_CONFIGS.values()) {
            for(ThemeKey key : config.toRegistry().getKeys()) {
                VaultRegistry.THEME.register(key);
            }
        }
        ((KeyRegistryAccessor)VaultRegistry.THEME).setLocked(true);
    }
}
