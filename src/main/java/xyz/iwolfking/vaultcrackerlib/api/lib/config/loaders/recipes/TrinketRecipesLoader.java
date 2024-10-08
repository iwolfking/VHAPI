package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.recipes;

import iskallia.vault.config.recipe.CatalystRecipesConfig;
import iskallia.vault.config.recipe.TrinketRecipesConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.ServerLifecycleHooks;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;

import java.util.HashMap;

public class TrinketRecipesLoader extends VaultConfigDataLoader<TrinketRecipesConfig> {
    public TrinketRecipesLoader(String namespace) {
        super(ModConfigs.TRINKET_RECIPES, "recipes/trinket", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(TrinketRecipesConfig config : this.CUSTOM_CONFIGS.values()) {
            ModConfigs.TRINKET_RECIPES.getConfigRecipes().addAll(config.getConfigRecipes());
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
