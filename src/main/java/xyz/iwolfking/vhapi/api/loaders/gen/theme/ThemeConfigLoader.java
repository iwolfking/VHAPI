package xyz.iwolfking.vhapi.api.loaders.gen.theme;

import iskallia.vault.config.core.ThemesConfig;
import iskallia.vault.core.Version;
import iskallia.vault.core.data.key.ThemeKey;
import iskallia.vault.core.vault.VaultRegistry;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.KeyRegistryAccessor;

public class ThemeConfigLoader extends VaultConfigProcessor<ThemesConfig> {
    public ThemeConfigLoader() {
        super(new ThemesConfig(), "themes");
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
        super.afterConfigsLoad(event);
    }
}
