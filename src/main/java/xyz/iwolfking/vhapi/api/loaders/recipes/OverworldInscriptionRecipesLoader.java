package xyz.iwolfking.vhapi.api.loaders.recipes;

import iskallia.vault.config.recipe.OverworldInscriptionRecipesConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.ServerLifecycleHooks;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.lib.core.processors.IRecipeSyncer;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

public class OverworldInscriptionRecipesLoader extends VaultConfigProcessor<OverworldInscriptionRecipesConfig> implements IRecipeSyncer {
    public OverworldInscriptionRecipesLoader() {
            super(new OverworldInscriptionRecipesConfig(), "vault_recipes/overworld_inscription");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ResourceLocation key : this.CUSTOM_CONFIGS.keySet()) {
            OverworldInscriptionRecipesConfig config = CUSTOM_CONFIGS.get(key);
            if(key.getPath().contains("overwrite")) {
                ModConfigs.OVERWORLD_INSCRIPTION_RECIPES = config;
            }
            else {
                ModConfigs.OVERWORLD_INSCRIPTION_RECIPES.getConfigRecipes().addAll(config.getConfigRecipes());
            }
        }
        syncRecipes();
        super.afterConfigsLoad(event);
    }

    public void syncRecipes() {
        MinecraftServer srv = ServerLifecycleHooks.getCurrentServer();
        if (srv != null) {
            srv.getPlayerList().getPlayers().forEach((player) -> {
                ModConfigs.OVERWORLD_INSCRIPTION_RECIPES.syncTo(ModConfigs.OVERWORLD_INSCRIPTION_RECIPES, player);
            });
        }
    }
}
