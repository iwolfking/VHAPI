package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.CompanionsConfig;
import iskallia.vault.config.TooltipConfig;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.CompanionsConfigAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.TooltipConfigAccessor;

import java.util.*;

public abstract class AbstractCompanionsProvider extends AbstractVaultConfigDataProvider<AbstractCompanionsProvider.Builder> {
    protected AbstractCompanionsProvider(DataGenerator generator, String modid) {
        super(generator, modid, "companions", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Companions Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<CompanionsConfig> {
        Map<String, Double> entries = new LinkedHashMap<>();

        public Builder() {
            super(CompanionsConfig::new);
        }

        public Builder addPet(String petId, Double weight) {
            entries.put(petId, weight);
            return this;
        }

        @Override
        protected void configureConfig(CompanionsConfig config) {
            ((CompanionsConfigAccessor)config).getPetWeights().putAll(entries);
        }
    }
}
