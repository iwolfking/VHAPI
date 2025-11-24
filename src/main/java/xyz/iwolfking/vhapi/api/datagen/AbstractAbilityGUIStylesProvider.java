package xyz.iwolfking.vhapi.api.datagen;

import com.google.gson.JsonObject;
import iskallia.vault.config.AbilitiesGUIConfig;
import iskallia.vault.config.entry.DescriptionData;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.AbilitiesGUIConfigAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.AbilitiesGUIConfigSpecializationStyleAccessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractAbilityGUIStylesProvider extends AbstractVaultConfigDataProvider<AbstractAbilityGUIStylesProvider.Builder> {
    protected AbstractAbilityGUIStylesProvider(DataGenerator generator, String modid) {
        super(generator, modid, "abilities/ability_gui", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Ability GUI Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<AbilitiesGUIConfig> {

        private final Map<String, AbilitiesGUIConfig.AbilityStyle> styles = new HashMap<>();

        private final List<AbilitiesGUIConfig.AbilityLabel> labels = new ArrayList<>();

        private final List<AbilitiesGUIConfig.Line> lines = new ArrayList<>();

        public Builder() {
            super(AbilitiesGUIConfig::new);
        }

        public Builder add(String abilityName, int x, int y, Consumer<StyleBuilder> abilityStyleBuilderConsumer) {
            StyleBuilder builder = new StyleBuilder(x, y);
            abilityStyleBuilderConsumer.accept(builder);
            styles.put(abilityName, builder.build());
            return this;
        }

        public Builder label(int x, int y, JsonObject labelDescription) {
            AbilitiesGUIConfig.AbilityLabel label = new AbilitiesGUIConfig.AbilityLabel(new DescriptionData(labelDescription), x, y);
            labels.add(label);
            return this;
        }

        public Builder line(int x, int y, int width, int height, int color) {
            AbilitiesGUIConfig.Line label = new AbilitiesGUIConfig.Line(x, y, width, height, color);
            lines.add(label);
            return this;
        }

        @Override
        public void configureConfig(AbilitiesGUIConfig config) {
            ((AbilitiesGUIConfigAccessor)config).setStyles(styles);
            ((AbilitiesGUIConfigAccessor)config).setLabels(labels);
            ((AbilitiesGUIConfigAccessor)config).setLines(lines);
        }

        public static class StyleBuilder {
            private final int x;
            private final int y;
            private final Map<String, AbilitiesGUIConfig.SpecializationStyle> specializationStyleMap = new HashMap<>();

            public StyleBuilder(int x, int y) {
                this.x = x;
                this.y = y;
            }

            public StyleBuilder specialization(String specializationName, ResourceLocation icon, AbilitiesGUIConfig.Type type) {
                AbilitiesGUIConfig.SpecializationStyle specializationStyle = new AbilitiesGUIConfig.SpecializationStyle(icon);
                ((AbilitiesGUIConfigSpecializationStyleAccessor)specializationStyle).setType(type);
                specializationStyleMap.put(specializationName, specializationStyle);
                return this;
            }

            public AbilitiesGUIConfig.AbilityStyle build() {
                return new AbilitiesGUIConfig.AbilityStyle(x, y,specializationStyleMap);
            }
        }

    }
}
