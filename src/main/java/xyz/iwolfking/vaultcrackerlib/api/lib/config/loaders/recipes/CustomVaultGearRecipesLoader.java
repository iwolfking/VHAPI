package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.recipes;

import iskallia.vault.config.recipe.GearRecipesConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.ServerLifecycleHooks;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;

import java.util.HashMap;

@Mod.EventBusSubscriber(modid = "vaultcrackerlib", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CustomVaultGearRecipesLoader extends VaultConfigDataLoader<GearRecipesConfig> {
    public CustomVaultGearRecipesLoader(String namespace) {
        super(ModConfigs.GEAR_RECIPES, "recipes/gear", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(GearRecipesConfig config : this.CUSTOM_CONFIGS.values()) {
            ModConfigs.GEAR_RECIPES.getConfigRecipes().addAll(config.getConfigRecipes());
        }
        syncRecipes();
    }

    private static void syncRecipes() {
        MinecraftServer srv = ServerLifecycleHooks.getCurrentServer();
        if (srv != null) {
            srv.getPlayerList().getPlayers().forEach((player) -> {
                ModConfigs.GEAR_RECIPES.syncTo(ModConfigs.GEAR_RECIPES, player);
            });
        }
    }

}
