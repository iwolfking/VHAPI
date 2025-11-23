package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.ResearchGroupConfig;
import iskallia.vault.config.ResearchGroupStyleConfig;
import iskallia.vault.config.entry.ResearchGroupStyle;
import iskallia.vault.research.group.ResearchGroup;
import net.minecraft.data.DataGenerator;

import java.util.function.Consumer;

public abstract class AbstractResearchGroupStyleProvider extends AbstractVaultConfigDataProvider {
    protected AbstractResearchGroupStyleProvider(DataGenerator generator, String modid) {
        super(generator, modid, "research/group_styles");
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Research Group Style Provider";
    }


    public static class ResearchGroupStyleBuilder {
        ResearchGroupStyleConfig researchConfig = new ResearchGroupStyleConfig();

        public ResearchGroupStyleBuilder addGroupStyle(String groupName, Consumer<ResearchGroupStyle.Builder> groupConsumer) {
            ResearchGroupStyle.Builder groupBuilder = ResearchGroupStyle.builder(groupName);
            groupConsumer.accept(groupBuilder);
            researchConfig.getStyles().put(groupName, groupBuilder.build());
            return this;
        }


        public ResearchGroupStyleConfig build() {
            return researchConfig;
        }

    }

}
