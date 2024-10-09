package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.recipes;

import iskallia.vault.config.recipe.CatalystRecipesConfig;
import iskallia.vault.config.recipe.ToolRecipesConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.ServerLifecycleHooks;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;

import java.util.HashMap;

public class ToolRecipesLoader extends VaultConfigDataLoader<ToolRecipesConfig> {
    public ToolRecipesLoader(String namespace) {
        super(new ToolRecipesConfig(), "vault_recipes/tool", new HashMap<>(), namespace);
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
    }

    private static void syncRecipes() {
        MinecraftServer srv = ServerLifecycleHooks.getCurrentServer();
        if (srv != null) {
            srv.getPlayerList().getPlayers().forEach((player) -> {
                ModConfigs.TOOL_RECIPES.syncTo(ModConfigs.TOOL_RECIPES, player);
            });
        }
    }
}

