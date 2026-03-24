package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.ResearchGroupConfig;
import iskallia.vault.research.group.ResearchGroup;
import net.minecraft.data.DataGenerator;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class AbstractResearchGroupProvider extends AbstractVaultConfigDataProvider<AbstractResearchGroupProvider.Builder> {
    protected AbstractResearchGroupProvider(DataGenerator generator, String modid) {
        super(generator, modid, "research/groups", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Research Group Data Provider";
    }


    public static class Builder extends VaultConfigBuilder<ResearchGroupConfig> {

        Map<String, ResearchGroup> groupMap = new HashMap<>();

        public Builder() {
            super(ResearchGroupConfig::new);
        }

        public Builder addGroup(String groupName, Consumer<ResearchGroup.Builder> groupConsumer) {
            ResearchGroup.Builder groupBuilder = ResearchGroup.builder(groupName);
            groupConsumer.accept(groupBuilder);
            groupMap.put(groupName, groupBuilder.build());
            return this;
        }


        @Override
        protected void configureConfig(ResearchGroupConfig config) {
            config.getGroups().putAll(groupMap);
        }

    }

}
