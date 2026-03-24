package xyz.iwolfking.vhapi.api.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import iskallia.vault.config.SkillDescriptionsConfig;
import net.minecraft.data.DataGenerator;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.SkillDescriptionsConfigAccessor;
import java.util.HashMap;
import java.util.function.Consumer;

public abstract class AbstractSkillDescriptionsProvider extends AbstractVaultConfigDataProvider<AbstractSkillDescriptionsProvider.Builder> {
    protected AbstractSkillDescriptionsProvider(DataGenerator generator, String modid) {
        super(generator, modid, "skill/descriptions", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Skill Descriptions Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<SkillDescriptionsConfig> {
        private final HashMap<String, JsonElement> descriptions = new HashMap<>();

        public Builder() {
            super(SkillDescriptionsConfig::new);
        }

        public Builder addDescription(String name, Consumer<JsonArray> descriptionConsumer) {
            JsonArray descriptionArray = new JsonArray();
            descriptionConsumer.accept(descriptionArray);
            descriptions.put(name, descriptionArray);
            return this;
        }

        @Override
        protected void configureConfig(SkillDescriptionsConfig config) {
            ((SkillDescriptionsConfigAccessor)config).setDescriptions(descriptions);

        }
    }
}
