package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.recipes;

import iskallia.vault.config.recipe.GearRecipesConfig;
import iskallia.vault.config.recipe.InscriptionRecipesConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.ServerLifecycleHooks;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;

import java.util.HashMap;
import java.util.Map;

public class InscriptionRecipesLoader extends VaultConfigDataLoader<InscriptionRecipesConfig> {
    public InscriptionRecipesLoader(String namespace) {
        super(ModConfigs.INSCRIPTION_RECIPES, "inscription_recipes", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(InscriptionRecipesConfig config : this.CUSTOM_CONFIGS.values()) {
            ModConfigs.INSCRIPTION_RECIPES.getConfigRecipes().addAll(config.getConfigRecipes());
        }
        syncRecipes();
    }

    private static void syncRecipes() {
        MinecraftServer srv = ServerLifecycleHooks.getCurrentServer();
        if (srv != null) {
            srv.getPlayerList().getPlayers().forEach((player) -> {
                ModConfigs.INSCRIPTION_RECIPES.syncTo(ModConfigs.INSCRIPTION_RECIPES, player);
            });
        }
    }
}
