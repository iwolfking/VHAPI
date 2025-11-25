package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.MenuPlayerStatDescriptionConfig;
import iskallia.vault.config.TooltipConfig;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.MenuPlayerStatDescriptionConfigAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.TooltipConfigAccessor;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public abstract class AbstractPlayerMenuStatDescriptionsProvider extends AbstractVaultConfigDataProvider<AbstractPlayerMenuStatDescriptionsProvider.Builder> {
    protected AbstractPlayerMenuStatDescriptionsProvider(DataGenerator generator, String modid) {
        super(generator, modid, "menu_player_stat_descriptions", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Player Menu Descriptions Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<MenuPlayerStatDescriptionConfig> {
        private final TreeMap<ResourceLocation, String> entries = new TreeMap<>();

        public Builder() {
            super(MenuPlayerStatDescriptionConfig::new);
        }

        public Builder add(ResourceLocation attributeId, String description) {
            entries.put(attributeId, description);
            return this;
        }

        @Override
        protected void configureConfig(MenuPlayerStatDescriptionConfig config) {
            ((MenuPlayerStatDescriptionConfigAccessor)config).setGearDescriptions(entries);
        }
    }
}
