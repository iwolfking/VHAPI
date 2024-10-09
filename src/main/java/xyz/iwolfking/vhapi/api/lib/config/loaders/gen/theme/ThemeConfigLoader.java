package xyz.iwolfking.vhapi.api.lib.config.loaders.gen.theme;

import iskallia.vault.config.core.ThemesConfig;
import iskallia.vault.core.Version;
import iskallia.vault.core.data.key.ThemeKey;
import iskallia.vault.core.vault.VaultRegistry;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vhapi.mixin.accessors.KeyRegistryAccessor;

import java.util.HashMap;

public class ThemeConfigLoader extends VaultConfigDataLoader<ThemesConfig> {
    public ThemeConfigLoader(String namespace) {
        super(new ThemesConfig(), "themes", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        ((KeyRegistryAccessor)VaultRegistry.THEME).setLocked(false);
        for(ThemesConfig config : this.CUSTOM_CONFIGS.values()) {
            for(ThemeKey key : config.toRegistry().getKeys()) {
                VaultRegistry.THEME.register(key.with(Version.latest(), key.get(Version.v1_0)));
            }
        }
        ((KeyRegistryAccessor)VaultRegistry.THEME).setLocked(true);
    }
}
