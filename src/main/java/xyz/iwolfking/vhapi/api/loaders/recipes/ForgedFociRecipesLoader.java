package xyz.iwolfking.vhapi.api.loaders.recipes;

import iskallia.vault.config.recipe.ForgedFociRecipesConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.ServerLifecycleHooks;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.lib.core.processors.IRecipeSyncer;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

public class ForgedFociRecipesLoader extends VaultConfigProcessor<ForgedFociRecipesConfig> implements IRecipeSyncer {
    public ForgedFociRecipesLoader() {
        super(new ForgedFociRecipesConfig(), "vault_recipes/forged_foci");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ResourceLocation key : this.CUSTOM_CONFIGS.keySet()) {
            ForgedFociRecipesConfig config = CUSTOM_CONFIGS.get(key);
            if(key.getPath().contains("overwrite")) {
                ModConfigs.FORGED_FOCI_RECIPES = config;
            }
            else {
                ModConfigs.FORGED_FOCI_RECIPES.getConfigRecipes().addAll(config.getConfigRecipes());
            }
        }
        syncRecipes();
        super.afterConfigsLoad(event);
    }


    public void syncRecipes() {
        MinecraftServer srv = ServerLifecycleHooks.getCurrentServer();
        if (srv != null) {
            srv.getPlayerList().getPlayers().forEach((player) -> {
                ModConfigs.FORGED_FOCI_RECIPES.syncTo(ModConfigs.FORGED_FOCI_RECIPES, player);
            });
        }
    }
}

