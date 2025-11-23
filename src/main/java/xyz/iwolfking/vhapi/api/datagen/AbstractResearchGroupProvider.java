package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.ResearchConfig;
import iskallia.vault.config.ResearchGroupConfig;
import iskallia.vault.research.group.ResearchGroup;
import iskallia.vault.research.type.CustomResearch;
import iskallia.vault.research.type.ModResearch;
import net.minecraft.data.DataGenerator;

import java.util.function.Consumer;

public abstract class AbstractResearchGroupProvider extends AbstractVaultConfigDataProvider {
    protected AbstractResearchGroupProvider(DataGenerator generator, String modid) {
        super(generator, modid, "research/groups");
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Research Group Data Provider";
    }


    public static class ResearchGroupBuilder {
        ResearchGroupConfig researchConfig = new ResearchGroupConfig();

        public ResearchGroupBuilder addGroup(String groupName, Consumer<ResearchGroup.Builder> groupConsumer) {
            ResearchGroup.Builder groupBuilder = ResearchGroup.builder(groupName);
            groupConsumer.accept(groupBuilder);
            researchConfig.getGroups().put(groupName, groupBuilder.build());
            return this;
        }


        public ResearchGroupConfig build() {
            return researchConfig;
        }

    }

}
