package xyz.iwolfking.vhapi.api.loaders.recipes;

import iskallia.vault.config.recipe.CatalystRecipesConfig;
import iskallia.vault.config.recipe.CrystalSealRecipesConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.ServerLifecycleHooks;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.lib.core.processors.IRecipeSyncer;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

public class CrystalSealRecipesLoader extends VaultConfigProcessor<CrystalSealRecipesConfig> implements IRecipeSyncer {
    public CrystalSealRecipesLoader() {
        super(new CrystalSealRecipesConfig(), "vault_recipes/crystal_seal");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ResourceLocation key : this.CUSTOM_CONFIGS.keySet()) {
            CrystalSealRecipesConfig config = CUSTOM_CONFIGS.get(key);
            if(key.getPath().contains("overwrite")) {
                ModConfigs.CRYSTAL_SEAL_RECIPES = config;
            }
            else {
                ModConfigs.CRYSTAL_SEAL_RECIPES.getConfigRecipes().addAll(config.getConfigRecipes());
            }
        }
        syncRecipes();
        super.afterConfigsLoad(event);
    }


    public void syncRecipes() {
        MinecraftServer srv = ServerLifecycleHooks.getCurrentServer();
        if (srv != null) {
            srv.getPlayerList().getPlayers().forEach((player) -> {
                ModConfigs.CRYSTAL_SEAL_RECIPES.syncTo(ModConfigs.CRYSTAL_SEAL_RECIPES, player);
            });
        }
    }
}

