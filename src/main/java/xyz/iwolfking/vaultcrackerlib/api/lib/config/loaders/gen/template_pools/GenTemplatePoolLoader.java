package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gen.template_pools;

import com.google.gson.JsonElement;
import iskallia.vault.core.world.generator.theme.ClassicVaultTheme;
import iskallia.vault.core.world.processor.Palette;
import iskallia.vault.core.world.template.data.TemplatePool;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.GenFileDataLoader;

import java.util.HashMap;
import java.util.Map;

public class GenTemplatePoolLoader extends GenFileDataLoader<TemplatePool> {
    public GenTemplatePoolLoader(String namespace) {
        super(TemplatePool.class, "gen/template_pools", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(TemplatePool theme : this.CUSTOM_CONFIGS.values()) {
            System.out.println("Loaded new template pool! " + theme.getPath());
            System.out.println(theme.getKey());
            System.out.println(theme.getRandom().isPresent());
            System.out.println(theme.flatten());
        }
    }
}
