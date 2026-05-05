package xyz.iwolfking.vhapi.api.loaders.recipes;

import iskallia.vault.config.AugmentStationConfig;
import iskallia.vault.config.recipe.CatalystRecipesConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.ServerLifecycleHooks;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.lib.core.processors.IRecipeSyncer;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.AugmentStationConfigAccessor;

public class AugmentStationLoader extends VaultConfigProcessor<AugmentStationConfig> {
    public AugmentStationLoader() {
        super(new AugmentStationConfig(), "vault_recipes/augment");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        CUSTOM_CONFIGS.forEach((resourceLocation, augmentStationConfig) -> {
            ((AugmentStationConfigAccessor)ModConfigs.AUGMENT_STATION).getThemes().addAll(((AugmentStationConfigAccessor)augmentStationConfig).getThemes());
            ((AugmentStationConfigAccessor)ModConfigs.AUGMENT_STATION).getRecipes().putAll(((AugmentStationConfigAccessor)augmentStationConfig).getRecipes());
        });
        super.afterConfigsLoad(event);
    }
}

