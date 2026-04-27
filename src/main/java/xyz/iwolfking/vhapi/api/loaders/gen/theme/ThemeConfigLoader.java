package xyz.iwolfking.vhapi.api.loaders.gen.theme;

import iskallia.vault.config.core.ThemesConfig;
import iskallia.vault.core.Version;
import iskallia.vault.core.data.key.ThemeKey;
import iskallia.vault.core.data.key.registry.KeyRegistry;
import iskallia.vault.core.vault.VaultRegistry;
import iskallia.vault.core.world.generator.theme.Theme;
import xyz.iwolfking.vhapi.api.events.VaultGenConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.KeyRegistryAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.KeyRegistryConfigAccessor;

public class ThemeConfigLoader extends VaultConfigProcessor<ThemesConfig> {
    public ThemeConfigLoader() {
        super(new ThemesConfig(), "themes");
    }

    @Override
    public void afterGenConfigsRegistriesBuilt(VaultGenConfigEvent.RegistriesBuilt event) {
        ((KeyRegistryAccessor) VaultRegistry.THEME).setLocked(false);
        for (ThemesConfig config : this.CUSTOM_CONFIGS.values()) {
            for (ThemeKey key : toRegistry(config).getKeys()) {
                VaultRegistry.THEME.register(key.with(Version.latest(), key.get(Version.v1_0)));
            }
        }
        ((KeyRegistryAccessor) VaultRegistry.THEME).setLocked(true);

        super.afterGenConfigsRegistriesBuilt(event);
    }

    public KeyRegistry<ThemeKey, Theme, Version> toRegistry(ThemesConfig config) {
        KeyRegistry<ThemeKey, Theme, Version> registry = config.create();
        ((KeyRegistryConfigAccessor<ThemeKey>)config).getKeys().forEach(registry::register);
        return registry;
    }
}
