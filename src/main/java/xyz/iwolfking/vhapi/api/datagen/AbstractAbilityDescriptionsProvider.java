package xyz.iwolfking.vhapi.api.datagen;

import com.google.gson.JsonArray;
import iskallia.vault.config.AbilitiesDescriptionsConfig;
import net.minecraft.data.DataGenerator;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.AbilitiesDescriptionsConfigAccessor;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.function.Consumer;

public abstract class AbstractAbilityDescriptionsProvider extends AbstractVaultConfigDataProvider<AbstractAbilityDescriptionsProvider.Builder> {
    protected AbstractAbilityDescriptionsProvider(DataGenerator generator, String modid) {
        super(generator, modid, "abilities/descriptions", Builder::new);
    }


    @Override
    public String getName() {
        return modid + " Ability Descriptions Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<AbilitiesDescriptionsConfig> {
        private TreeMap<String, AbilitiesDescriptionsConfig.DescriptionData> data;

        public Builder() {
            super(AbilitiesDescriptionsConfig::new);
        }

        public Builder addDescription(String name, Consumer<JsonArray> descriptionConsumer, Consumer<List<String>> currentConsumer, Consumer<List<String>> nextConsumer) {
            JsonArray descriptionArray = new JsonArray();
            descriptionConsumer.accept(descriptionArray);
            List<String> current = new ArrayList<>();
            currentConsumer.accept(current);
            List<String> next = new ArrayList<>();
            nextConsumer.accept(next);
            data.put(name, new AbilitiesDescriptionsConfig.DescriptionData(descriptionArray, current, next));
            return this;
        }

        @Override
        protected void configureConfig(AbilitiesDescriptionsConfig config) {
            ((AbilitiesDescriptionsConfigAccessor)config).setData(data);
        }

    }
}
