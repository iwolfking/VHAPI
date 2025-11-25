package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.TooltipConfig;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.config.ResearchExclusionConfig;
import xyz.iwolfking.vhapi.api.datagen.lib.BasicListBuilder;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.TooltipConfigAccessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractResearchExclusionsProvider extends AbstractVaultConfigDataProvider<AbstractResearchExclusionsProvider.Builder> {
    protected AbstractResearchExclusionsProvider(DataGenerator generator, String modid) {
        super(generator, modid, "research/exclusions", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Research Exclusions Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<ResearchExclusionConfig> {
        private final Map<String, List<String>> RESEARCH_EXCLUSIONS = new HashMap<>();

        public Builder() {
            super(ResearchExclusionConfig::new);
        }

        public Builder add(String researchName, Consumer<BasicListBuilder<String>> builderConsumer) {
            BasicListBuilder<String> builder = new BasicListBuilder<>();
            RESEARCH_EXCLUSIONS.put(researchName, builder.build());
            return this;
        }

        @Override
        protected void configureConfig(ResearchExclusionConfig config) {
            config.RESEARCH_EXCLUSIONS.putAll(RESEARCH_EXCLUSIONS);
        }
    }
}
