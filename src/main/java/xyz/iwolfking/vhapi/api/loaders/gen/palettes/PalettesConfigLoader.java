package xyz.iwolfking.vhapi.api.loaders.gen.palettes;

import iskallia.vault.config.core.PalettesConfig;
import iskallia.vault.core.Version;
import iskallia.vault.core.data.key.PaletteKey;
import iskallia.vault.core.data.key.TemplatePoolKey;
import iskallia.vault.core.vault.VaultRegistry;
import iskallia.vault.core.world.processor.Palette;
import iskallia.vault.core.world.template.data.TemplatePool;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.api.util.ResourceLocUtils;
import xyz.iwolfking.vhapi.mixin.accessors.KeyRegistryAccessor;

public class PalettesConfigLoader extends VaultConfigProcessor<PalettesConfig> {
    public PalettesConfigLoader() {
        super(new PalettesConfig(), "palettes");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        ((KeyRegistryAccessor) VaultRegistry.PALETTE).setLocked(false);
        for(PalettesConfig config : this.CUSTOM_CONFIGS.values()) {
            for(PaletteKey key : config.toRegistry().getKeys()) {
                if(key.getId().getPath().endsWith("_merge")) {
                    PaletteKey existingKey = VaultRegistry.PALETTE.getKey(ResourceLocUtils.removeSuffixFromId("_merge", key.getId()));
                    Palette existingPool = existingKey.get(Version.latest());
                    Palette mergePool = key.get(Version.v1_0);
                    mergePool.getTileProcessors().forEach((o) -> {
                        existingPool.getTileProcessors().add(o);
                    });
                    mergePool.getEntityProcessors().forEach((o) -> {
                        existingPool.getEntityProcessors().add(o);
                    });

                    VaultRegistry.PALETTE.register(existingKey.with(Version.latest(), existingPool));
                }
                else {
                    VaultRegistry.PALETTE.register(key.with(Version.latest(), key.get(Version.v1_0)));
                }
            }
        }
        ((KeyRegistryAccessor)VaultRegistry.PALETTE).setLocked(true);
        super.afterConfigsLoad(event);
    }
}
