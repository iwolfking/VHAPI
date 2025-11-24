package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.ResearchGroupStyleConfig;
import iskallia.vault.config.entry.ResearchGroupStyle;
import net.minecraft.data.DataGenerator;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class AbstractResearchGroupStyleProvider extends AbstractVaultConfigDataProvider<AbstractResearchGroupStyleProvider.Builder> {
    protected AbstractResearchGroupStyleProvider(DataGenerator generator, String modid) {
        super(generator, modid, "research/group_styles", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Research Group Style Provider";
    }


    public static class Builder extends VaultConfigBuilder<ResearchGroupStyleConfig> {
        Map<String, ResearchGroupStyle> groupStyles = new HashMap<>();

        public Builder() {
            super(ResearchGroupStyleConfig::new);
        }

        public Builder addGroupStyle(String groupName, Consumer<ResearchGroupStyle.Builder> groupConsumer) {
            ResearchGroupStyle.Builder groupBuilder = ResearchGroupStyle.builder(groupName);
            groupConsumer.accept(groupBuilder);
            groupStyles.put(groupName, groupBuilder.build());
            return this;
        }


        @Override
        protected void configureConfig(ResearchGroupStyleConfig config) {
            config.getStyles().putAll(groupStyles);
        }

    }

}
