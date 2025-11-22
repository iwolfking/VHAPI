package xyz.iwolfking.vhapi.api.lib.core.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import iskallia.vault.config.SkillDescriptionsConfig;
import net.minecraft.data.DataGenerator;
import xyz.iwolfking.vhapi.mixin.accessors.SkillDescriptionsConfigAccessor;
import java.util.HashMap;
import java.util.function.Consumer;

public abstract class AbstractSkillDescriptionsProvider extends AbstractVaultConfigDataProvider {
    protected AbstractSkillDescriptionsProvider(DataGenerator generator, String modid) {
        super(generator, modid, "skill/descriptions");
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Skill Descriptions Data Provider";
    }

    public static class SkillDescriptionsConfigBuilder {
        private final HashMap<String, JsonElement> descriptions = new HashMap<>();

        public SkillDescriptionsConfigBuilder addDescription(String name, Consumer<JsonArray> descriptionConsumer) {
            JsonArray descriptionArray = new JsonArray();
            descriptionConsumer.accept(descriptionArray);
            descriptions.put(name, descriptionArray);
            return this;
        }

        public SkillDescriptionsConfig build() {
            SkillDescriptionsConfig newConfig = new SkillDescriptionsConfig();
            ((SkillDescriptionsConfigAccessor)newConfig).setDescriptions(descriptions);
            return newConfig;
        }

    }
}
