package xyz.iwolfking.vhapi.api.lib.config.loaders.gen.palettes;

import iskallia.vault.config.core.PalettesConfig;
import iskallia.vault.core.Version;
import iskallia.vault.core.data.key.PaletteKey;
import iskallia.vault.core.vault.VaultRegistry;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vhapi.mixin.accessors.KeyRegistryAccessor;

import java.util.HashMap;

public class PalettesConfigLoader extends VaultConfigDataLoader<PalettesConfig> {
    public PalettesConfigLoader(String namespace) {
        super(new PalettesConfig(), "palettes", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        ((KeyRegistryAccessor) VaultRegistry.PALETTE).setLocked(false);
        for(PalettesConfig config : this.CUSTOM_CONFIGS.values()) {
            for(PaletteKey key : config.toRegistry().getKeys()) {
                VaultRegistry.PALETTE.register(key.with(Version.latest(), key.get(Version.v1_0)));
            }
        }
        ((KeyRegistryAccessor)VaultRegistry.PALETTE).setLocked(true);
    }
}
