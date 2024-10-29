package xyz.iwolfking.vhapi.api.loaders.recipes;

import iskallia.vault.config.recipe.CatalystRecipesConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.ServerLifecycleHooks;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.api.lib.core.processors.IRecipeSyncer;

public class CatalystRecipesLoader extends VaultConfigProcessor<CatalystRecipesConfig> implements IRecipeSyncer {
    public CatalystRecipesLoader() {
        super(new CatalystRecipesConfig(), "vault_recipes/catalyst");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ResourceLocation key : this.CUSTOM_CONFIGS.keySet()) {
            CatalystRecipesConfig config = CUSTOM_CONFIGS.get(key);
            if(key.getPath().contains("overwrite")) {
                ModConfigs.CATALYST_RECIPES = config;
            }
            else {
                ModConfigs.CATALYST_RECIPES.getConfigRecipes().addAll(config.getConfigRecipes());
            }
        }
        syncRecipes();
        super.afterConfigsLoad(event);
    }


    public void syncRecipes() {
        MinecraftServer srv = ServerLifecycleHooks.getCurrentServer();
        if (srv != null) {
            srv.getPlayerList().getPlayers().forEach((player) -> {
                ModConfigs.CATALYST_RECIPES.syncTo(ModConfigs.CATALYST_RECIPES, player);
            });
        }
    }
}

