package xyz.iwolfking.vhapi.api.lib.config.loaders.recipes;

import iskallia.vault.config.recipe.TrinketRecipesConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.ServerLifecycleHooks;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.lib.loaders.VaultConfigDataLoader;

import java.util.HashMap;

public class TrinketRecipesLoader extends VaultConfigDataLoader<TrinketRecipesConfig> {
    public TrinketRecipesLoader(String namespace) {
        super(new TrinketRecipesConfig(), "vault_recipes/trinket", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ResourceLocation key : this.CUSTOM_CONFIGS.keySet()) {
            TrinketRecipesConfig config = CUSTOM_CONFIGS.get(key);
            if(key.getPath().contains("overwrite")) {
                ModConfigs.TRINKET_RECIPES = config;
            }
            else {
                ModConfigs.TRINKET_RECIPES.getConfigRecipes().addAll(config.getConfigRecipes());
            }
        }
        syncRecipes();
    }

    private static void syncRecipes() {
        MinecraftServer srv = ServerLifecycleHooks.getCurrentServer();
        if (srv != null) {
            srv.getPlayerList().getPlayers().forEach((player) -> {
                ModConfigs.TRINKET_RECIPES.syncTo(ModConfigs.TRINKET_RECIPES, player);
            });
        }
    }
}

