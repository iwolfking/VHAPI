package xyz.iwolfking.vhapi.api.loaders.recipes;

import iskallia.vault.config.recipe.JewelCraftingRecipesConfig;
import iskallia.vault.config.recipe.ToolRecipesConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.ServerLifecycleHooks;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.lib.core.processors.IRecipeSyncer;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

public class JewelCraftingRecipesLoader extends VaultConfigProcessor<JewelCraftingRecipesConfig> implements IRecipeSyncer {
    public JewelCraftingRecipesLoader() {
        super(new JewelCraftingRecipesConfig(), "vault_recipes/jewel");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ResourceLocation key : this.CUSTOM_CONFIGS.keySet()) {
            JewelCraftingRecipesConfig config = CUSTOM_CONFIGS.get(key);
            if(key.getPath().contains("overwrite")) {
                ModConfigs.JEWEL_CRAFTING_RECIPES = config;
            }
            else {
                ModConfigs.JEWEL_CRAFTING_RECIPES.getConfigRecipes().addAll(config.getConfigRecipes());
            }
        }
        syncRecipes();
        super.afterConfigsLoad(event);
    }

    public void syncRecipes() {
        MinecraftServer srv = ServerLifecycleHooks.getCurrentServer();
        if (srv != null) {
            srv.getPlayerList().getPlayers().forEach((player) -> {
                ModConfigs.JEWEL_CRAFTING_RECIPES.syncTo(ModConfigs.JEWEL_CRAFTING_RECIPES, player);
            });
        }
    }
}

