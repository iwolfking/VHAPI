package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.recipes;

import iskallia.vault.config.recipe.CatalystRecipesConfig;
import iskallia.vault.config.recipe.InscriptionRecipesConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.ServerLifecycleHooks;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;

import java.util.HashMap;

public class CatalystRecipesLoader extends VaultConfigDataLoader<CatalystRecipesConfig> {
    public CatalystRecipesLoader(String namespace) {
        super(ModConfigs.CATALYST_RECIPES, "recipes/catalyst", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(CatalystRecipesConfig config : this.CUSTOM_CONFIGS.values()) {
            ModConfigs.CATALYST_RECIPES.getConfigRecipes().addAll(config.getConfigRecipes());
        }
        syncRecipes();
    }

    private static void syncRecipes() {
        MinecraftServer srv = ServerLifecycleHooks.getCurrentServer();
        if (srv != null) {
            srv.getPlayerList().getPlayers().forEach((player) -> {
                ModConfigs.CATALYST_RECIPES.syncTo(ModConfigs.CATALYST_RECIPES, player);
            });
        }
    }
}

