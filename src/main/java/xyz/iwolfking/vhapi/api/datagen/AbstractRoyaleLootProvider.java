package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.RoyaleLootConfig;
import iskallia.vault.config.TooltipConfig;
import iskallia.vault.core.util.WeightedList;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.TooltipConfigAccessor;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRoyaleLootProvider extends AbstractVaultConfigDataProvider<AbstractRoyaleLootProvider.Builder> {
    protected AbstractRoyaleLootProvider(DataGenerator generator, String modid) {
        super(generator, modid, "royale_loot", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Royale Loot Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<RoyaleLootConfig> {
        private final WeightedList<ResourceLocation> presets = new WeightedList<>();
        private int numberOfSelections = 3;

        public Builder() {
            super(RoyaleLootConfig::new);
        }

        public Builder add(ResourceLocation id, int weight) {
            presets.add(id, weight);
            return this;
        }

        public Builder numberOfSelections(int number) {
            this.numberOfSelections = number;
            return this;
        }

        @Override
        protected void configureConfig(RoyaleLootConfig config) {
            config.presets = presets;
            config.numberOfSelections = numberOfSelections;
        }
    }
}
