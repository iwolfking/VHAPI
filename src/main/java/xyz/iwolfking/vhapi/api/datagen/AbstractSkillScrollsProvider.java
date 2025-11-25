package xyz.iwolfking.vhapi.api.datagen;

import com.google.gson.annotations.Expose;
import iskallia.vault.config.SkillGatesConfig;
import iskallia.vault.config.SkillScrollConfig;
import iskallia.vault.config.TooltipConfig;
import iskallia.vault.util.data.WeightedList;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.SkillScrollConfigAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.TooltipConfigAccessor;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSkillScrollsProvider extends AbstractVaultConfigDataProvider<AbstractSkillScrollsProvider.Builder> {
    protected AbstractSkillScrollsProvider(DataGenerator generator, String modid) {
        super(generator, modid, "skill/scrolls", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Skill Scrolls Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<SkillScrollConfig> {
        private final WeightedList<String> abilities = new WeightedList<>();
        private final WeightedList<String> talents = new WeightedList<>();
        private final WeightedList<ResourceLocation> modifiers = new WeightedList<>();

        public Builder() {
            super(SkillScrollConfig::new);
        }

        public Builder addAbility(String abilityName, int weight) {
            abilities.add(abilityName, weight);
            return this;
        }

        public Builder addTalent(String talentName, int weight) {
            talents.add(talentName, weight);
            return this;
        }

        public Builder addModifier(ResourceLocation modifierId, int weight) {
            modifiers.add(modifierId, weight);
            return this;
        }

        @Override
        protected void configureConfig(SkillScrollConfig config) {
            ((SkillScrollConfigAccessor)config).setAbilities(abilities);
            ((SkillScrollConfigAccessor)config).setTalents(talents);
            ((SkillScrollConfigAccessor)config).setModifiers(modifiers);
        }
    }
}
