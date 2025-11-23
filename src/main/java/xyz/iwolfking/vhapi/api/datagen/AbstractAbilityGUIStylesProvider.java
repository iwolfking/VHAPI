package xyz.iwolfking.vhapi.api.datagen;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import iskallia.vault.config.AbilitiesGUIConfig;
import iskallia.vault.config.TooltipConfig;
import iskallia.vault.config.entry.DescriptionData;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.mixin.accessors.AbilitiesGUIConfigSpecializationStyleAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.TooltipConfigAccessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractAbilityGUIStylesProvider extends AbstractVaultConfigDataProvider {
    protected AbstractAbilityGUIStylesProvider(DataGenerator generator, String modid) {
        super(generator, modid, "abilities/ability_gui");
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Ability GUI Data Provider";
    }

    public static class AbilityGUIConfigBuilder {

        private final Map<String, AbilitiesGUIConfig.AbilityStyle> styles = new HashMap<>();

        private final List<AbilitiesGUIConfig.AbilityLabel> labels = new ArrayList<>();

        private final List<AbilitiesGUIConfig.Line> lines = new ArrayList<>();

        public AbilityGUIConfigBuilder add(String abilityName, int x, int y, Consumer<AbilityStyleBuilder> abilityStyleBuilderConsumer) {
            AbilityStyleBuilder builder = new AbilityStyleBuilder(x, y);
            abilityStyleBuilderConsumer.accept(builder);
            styles.put(abilityName, builder.build());
            return this;
        }

        public AbilityGUIConfigBuilder label(int x, int y, JsonObject labelDescription) {
            AbilitiesGUIConfig.AbilityLabel label = new AbilitiesGUIConfig.AbilityLabel(new DescriptionData(labelDescription), x, y);
            labels.add(label);
            return this;
        }

        public AbilityGUIConfigBuilder line(int x, int y, int width, int height, int color) {
            AbilitiesGUIConfig.Line label = new AbilitiesGUIConfig.Line(x, y, width, height, color);
            lines.add(label);
            return this;
        }

        public AbilitiesGUIConfig build() {
            AbilitiesGUIConfig newConfig = new AbilitiesGUIConfig();
            newConfig.getStyles().putAll(styles);
            return newConfig;
        }

        public static class AbilityStyleBuilder {
            private final int x;
            private final int y;
            private final Map<String, AbilitiesGUIConfig.SpecializationStyle> specializationStyleMap = new HashMap<>();

            public AbilityStyleBuilder(int x, int y) {
                this.x = x;
                this.y = y;
            }

            public AbilityStyleBuilder specialization(String specializationName, ResourceLocation icon, AbilitiesGUIConfig.Type type) {
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
