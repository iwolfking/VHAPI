package xyz.iwolfking.vhapi.api.loaders.recipes;

import iskallia.vault.config.recipe.ToolRecipesConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.ServerLifecycleHooks;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.api.lib.core.processors.IRecipeSyncer;

public class ToolRecipesLoader extends VaultConfigProcessor<ToolRecipesConfig> implements IRecipeSyncer {
    public ToolRecipesLoader() {
        super(new ToolRecipesConfig(), "vault_recipes/tool");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ResourceLocation key : this.CUSTOM_CONFIGS.keySet()) {
            ToolRecipesConfig config = CUSTOM_CONFIGS.get(key);
            if(key.getPath().contains("overwrite")) {
                ModConfigs.TOOL_RECIPES = config;
            }
            else {
                ModConfigs.TOOL_RECIPES.getConfigRecipes().addAll(config.getConfigRecipes());
            }
        }
        syncRecipes();
        super.afterConfigsLoad(event);
    }

    public void syncRecipes() {
        MinecraftServer srv = ServerLifecycleHooks.getCurrentServer();
        if (srv != null) {
            srv.getPlayerList().getPlayers().forEach((player) -> {
                ModConfigs.TOOL_RECIPES.syncTo(ModConfigs.TOOL_RECIPES, player);
            });
        }
    }
}

