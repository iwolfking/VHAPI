package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.ResearchesGUIConfig;
import iskallia.vault.config.entry.SkillStyle;
import net.minecraft.data.DataGenerator;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public abstract class AbstractResearchStyleProvider extends AbstractVaultConfigDataProvider<AbstractResearchStyleProvider.Builder> {
    protected AbstractResearchStyleProvider(DataGenerator generator, String modid) {
        super(generator, modid, "research/research_styles", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Research Style Provider";
    }


    public static class Builder extends VaultConfigBuilder<ResearchesGUIConfig> {
        Map<String, SkillStyle> researchConfig = new HashMap<>();

        public Builder() {
            super(ResearchesGUIConfig::new);
        }

        public Builder addStyle(String researchName, SkillStyle style) {
            researchConfig.put(researchName, style);
            return this;
        }


        @Override
        protected void configureConfig(ResearchesGUIConfig config) {
            config.getStyles().putAll(researchConfig);
        }

    }

}
