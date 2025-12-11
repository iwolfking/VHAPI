package xyz.iwolfking.vhapi.api.loaders.recipes;

import iskallia.vault.config.recipe.CatalystRecipesConfig;
import iskallia.vault.config.recipe.DeckCraftingRecipesConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.ServerLifecycleHooks;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.lib.core.processors.IRecipeSyncer;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

public class DeckRecipesLoader extends VaultConfigProcessor<DeckCraftingRecipesConfig> implements IRecipeSyncer {
    public DeckRecipesLoader() {
        super(new DeckCraftingRecipesConfig(), "vault_recipes/deck");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ResourceLocation key : this.CUSTOM_CONFIGS.keySet()) {
            DeckCraftingRecipesConfig config = CUSTOM_CONFIGS.get(key);
            if(key.getPath().contains("overwrite")) {
                ModConfigs.DECK_CRAFTING_RECIPES = config;
            }
            else {
                ModConfigs.DECK_CRAFTING_RECIPES.getConfigRecipes().addAll(config.getConfigRecipes());
            }
        }
        syncRecipes();
        super.afterConfigsLoad(event);
    }


    public void syncRecipes() {
        MinecraftServer srv = ServerLifecycleHooks.getCurrentServer();
        if (srv != null) {
            srv.getPlayerList().getPlayers().forEach((player) -> {
                ModConfigs.DECK_CRAFTING_RECIPES.syncTo(ModConfigs.DECK_CRAFTING_RECIPES, player);
            });
        }
    }
}

