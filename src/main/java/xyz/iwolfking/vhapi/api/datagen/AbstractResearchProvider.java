package xyz.iwolfking.vhapi.api.datagen;

import com.google.gson.annotations.Expose;
import iskallia.vault.config.ResearchConfig;
import iskallia.vault.config.ResearchGroupConfig;
import iskallia.vault.config.ResearchGroupStyleConfig;
import iskallia.vault.research.type.CustomResearch;
import iskallia.vault.research.type.ModResearch;
import net.minecraft.data.DataGenerator;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractResearchProvider extends AbstractVaultConfigDataProvider {
    protected AbstractResearchProvider(DataGenerator generator, String modid) {
        super(generator, modid, "research/researches");
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Research Data Provider";
    }


    public static class ResearchBuilder {
        public List<ModResearch> MOD_RESEARCHES = new LinkedList<>();

        public List<CustomResearch> CUSTOM_RESEARCHES = new LinkedList<>();

        public ResearchBuilder addResearch(ModResearch research) {
            MOD_RESEARCHES.add(research);
            return this;
        }

        public ResearchBuilder addResearch(CustomResearch research) {
            CUSTOM_RESEARCHES.add(research);
            return this;
        }


        public ResearchConfig build() {
            ResearchConfig researchConfig = new ResearchConfig();
            researchConfig.CUSTOM_RESEARCHES.addAll(CUSTOM_RESEARCHES);
            researchConfig.MOD_RESEARCHES.addAll(MOD_RESEARCHES);
            return researchConfig;
        }

    }

}
