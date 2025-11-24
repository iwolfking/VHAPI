package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.ResearchConfig;
import iskallia.vault.research.type.CustomResearch;
import iskallia.vault.research.type.ModResearch;
import net.minecraft.data.DataGenerator;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractResearchProvider extends AbstractVaultConfigDataProvider<AbstractResearchProvider.Builder> {
    protected AbstractResearchProvider(DataGenerator generator, String modid) {
        super(generator, modid, "research/researches", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Research Data Provider";
    }


    public static class Builder extends VaultConfigBuilder<ResearchConfig> {
        public List<ModResearch> MOD_RESEARCHES = new LinkedList<>();

        public List<CustomResearch> CUSTOM_RESEARCHES = new LinkedList<>();

        public Builder() {
            super(ResearchConfig::new);
        }

        public Builder addResearch(ModResearch research) {
            MOD_RESEARCHES.add(research);
            return this;
        }

        public Builder addResearch(CustomResearch research) {
            CUSTOM_RESEARCHES.add(research);
            return this;
        }


        @Override
        protected void configureConfig(ResearchConfig config) {
            config.CUSTOM_RESEARCHES.addAll(CUSTOM_RESEARCHES);
            config.MOD_RESEARCHES.addAll(MOD_RESEARCHES);
        }

    }

}
