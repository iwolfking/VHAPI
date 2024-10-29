package xyz.iwolfking.vhapi.api.loaders.recipes;

import iskallia.vault.config.recipe.InscriptionRecipesConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.ServerLifecycleHooks;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.api.lib.core.processors.IRecipeSyncer;

public class InscriptionRecipesLoader extends VaultConfigProcessor<InscriptionRecipesConfig> implements IRecipeSyncer {
    public InscriptionRecipesLoader() {
        super(new InscriptionRecipesConfig(), "vault_recipes/inscription");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ResourceLocation key : this.CUSTOM_CONFIGS.keySet()) {
            InscriptionRecipesConfig config = CUSTOM_CONFIGS.get(key);
            if(key.getPath().contains("overwrite")) {
                ModConfigs.INSCRIPTION_RECIPES = config;
            }
            else {
                ModConfigs.INSCRIPTION_RECIPES.getConfigRecipes().addAll(config.getConfigRecipes());
            }
        }
        syncRecipes();
        super.afterConfigsLoad(event);
    }

    public void syncRecipes() {
        MinecraftServer srv = ServerLifecycleHooks.getCurrentServer();
        if (srv != null) {
            srv.getPlayerList().getPlayers().forEach((player) -> {
                ModConfigs.INSCRIPTION_RECIPES.syncTo(ModConfigs.INSCRIPTION_RECIPES, player);
            });
        }
    }
}
