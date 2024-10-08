package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gen.palettes;

import iskallia.vault.core.world.generator.theme.ClassicVaultTheme;
import iskallia.vault.core.world.generator.theme.Theme;
import iskallia.vault.core.world.processor.Palette;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.GenFileDataLoader;

import java.util.HashMap;
import java.util.Map;

public class GenPaletteLoader extends GenFileDataLoader<Palette> {
    public GenPaletteLoader(String namespace) {
        super(Palette.class, "gen/palettes", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(Palette theme : this.CUSTOM_CONFIGS.values()) {
            System.out.println("Loaded new palette! " + theme.getPath());
            System.out.println(theme.getEntityProcessors());
            System.out.println(theme.getTileProcessors());
        }
    }

}
