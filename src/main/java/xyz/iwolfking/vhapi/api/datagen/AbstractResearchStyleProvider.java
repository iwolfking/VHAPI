package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.ResearchGroupStyleConfig;
import iskallia.vault.config.ResearchesGUIConfig;
import iskallia.vault.config.entry.ResearchGroupStyle;
import iskallia.vault.config.entry.SkillStyle;
import net.minecraft.data.DataGenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractResearchStyleProvider extends AbstractVaultConfigDataProvider {
    protected AbstractResearchStyleProvider(DataGenerator generator, String modid) {
        super(generator, modid, "research/research_styles");
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Research Style Provider";
    }


    public static class ResearchStyleBuilder {
        Map<String, SkillStyle> researchConfig = new HashMap<>();

        public ResearchStyleBuilder addStyle(String researchName, SkillStyle style) {
            researchConfig.put(researchName, style);
            return this;
        }


        public ResearchesGUIConfig build() {
            ResearchesGUIConfig researchesGUIConfig = new ResearchesGUIConfig();
            researchesGUIConfig.getStyles().putAll(researchConfig);
            return researchesGUIConfig;
        }

    }

}
