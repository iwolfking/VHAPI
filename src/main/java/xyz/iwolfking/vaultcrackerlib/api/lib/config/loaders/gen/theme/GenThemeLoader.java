package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gen.theme;

import com.google.gson.JsonElement;
import iskallia.vault.config.core.TemplatePoolsConfig;
import iskallia.vault.config.core.ThemesConfig;
import iskallia.vault.core.data.key.TemplatePoolKey;
import iskallia.vault.core.data.key.ThemeKey;
import iskallia.vault.core.data.key.VersionedKey;
import iskallia.vault.core.vault.VaultRegistry;
import iskallia.vault.core.world.generator.theme.ClassicVaultTheme;
import iskallia.vault.core.world.generator.theme.Theme;
import iskallia.vault.item.crystal.theme.ValueCrystalTheme;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import xyz.iwolfking.vaultcrackerlib.api.LoaderRegistry;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.GenFileReader;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.GenFileDataLoader;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.KeyRegistryAccessor;

import java.util.HashMap;
import java.util.Map;

public class GenThemeLoader extends GenFileDataLoader<Theme> {
    public GenThemeLoader(String namespace) {
        super(Theme.class, "gen/themes", new HashMap<>(), namespace);
    }


    @Override
    protected void apply(Map<ResourceLocation, JsonElement> dataMap, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        for(TemplatePoolKey key : VaultRegistry.TEMPLATE_POOL.getKeys()) {
            System.out.println(key.getId());
        }
        super.apply(dataMap, resourceManager, profilerFiller);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(Theme theme : this.CUSTOM_CONFIGS.values()) {
            System.out.println("Loaded new theme! " + theme.getPath());
            System.out.println(theme.getAmbientLight());
            System.out.println(theme.getEffects());
            System.out.println(theme.getFoliageColor());
            System.out.println(theme.getPath());
            if(theme instanceof ClassicVaultTheme classicVaultTheme) {
                System.out.println(classicVaultTheme.getRooms());
                System.out.println(classicVaultTheme.getStarts());
                System.out.println(classicVaultTheme.getTunnels());
            }
        }
    }
}
