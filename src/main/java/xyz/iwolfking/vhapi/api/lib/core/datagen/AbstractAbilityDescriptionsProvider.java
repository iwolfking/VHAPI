package xyz.iwolfking.vhapi.api.lib.core.datagen;

import com.google.gson.JsonArray;
import iskallia.vault.config.AbilitiesDescriptionsConfig;
import net.minecraft.data.DataGenerator;
import xyz.iwolfking.vhapi.mixin.accessors.AbilitiesDescriptionsConfigAccessor;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.function.Consumer;

public abstract class AbstractAbilityDescriptionsProvider extends AbstractVaultConfigDataProvider {
    protected AbstractAbilityDescriptionsProvider(DataGenerator generator, String modid) {
        super(generator, modid, "abilities/descriptions");
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Ability Descriptions Data Provider";
    }

    public static class AbilityDescriptionsDataProvider {
        private TreeMap<String, AbilitiesDescriptionsConfig.DescriptionData> data;

        public AbilityDescriptionsDataProvider addDescription(String name, Consumer<JsonArray> descriptionConsumer, Consumer<List<String>> currentConsumer, Consumer<List<String>> nextConsumer) {
            JsonArray descriptionArray = new JsonArray();
            descriptionConsumer.accept(descriptionArray);
            List<String> current = new ArrayList<>();
            currentConsumer.accept(current);
            List<String> next = new ArrayList<>();
            nextConsumer.accept(next);
            data.put(name, new AbilitiesDescriptionsConfig.DescriptionData(descriptionArray, current, next));
            return this;
        }

        public AbilitiesDescriptionsConfig build() {
            AbilitiesDescriptionsConfig newConfig = new AbilitiesDescriptionsConfig();
            ((AbilitiesDescriptionsConfigAccessor)newConfig).getData().putAll(data);
            return newConfig;
        }

    }
}
