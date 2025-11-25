package xyz.iwolfking.vhapi.api.datagen;

import com.google.gson.annotations.Expose;
import iskallia.vault.config.RoyalePresetConfig;
import iskallia.vault.config.TooltipConfig;
import iskallia.vault.config.entry.LevelEntryList;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.datagen.lib.BasicListBuilder;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.api.datagen.lib.WeightedListBuilder;
import xyz.iwolfking.vhapi.api.util.builder.description.DescriptionDataBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.TooltipConfigAccessor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractRoyalePresetsProvider extends AbstractVaultConfigDataProvider<AbstractRoyalePresetsProvider.Builder> {
    protected AbstractRoyalePresetsProvider(DataGenerator generator, String modid) {
        super(generator, modid, "royale_presets", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Royale Presets Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<RoyalePresetConfig> {
        @Expose
        private final LevelEntryList<RoyalePresetConfig.Level> LEVELS = new LevelEntryList<>();

        private int skillPresetSize = 3;
        private int blueTrinketPresetSize = 3;
        private int redTrinketPresetSize = 3;

        public Builder() {
            super(RoyalePresetConfig::new);
        }

        public Builder add(int level, Consumer<SkillPresetBuilder> builderConsumer, Consumer<WeightedListBuilder<ResourceLocation>> redTrinketsConsumer, Consumer<WeightedListBuilder<ResourceLocation>> blueTrinketsConsumer) {
            SkillPresetBuilder builder = new SkillPresetBuilder();
            WeightedListBuilder<ResourceLocation> redTrinkets = new WeightedListBuilder<>();
            WeightedListBuilder<ResourceLocation> blueTrinkets = new WeightedListBuilder<>();
            builderConsumer.accept(builder);
            redTrinketsConsumer.accept(redTrinkets);
            blueTrinketsConsumer.accept(blueTrinkets);
            RoyalePresetConfig.Level presets = new RoyalePresetConfig.Level(level);
            builder.build().forEach((preset, aDouble) -> {
                presets.skillPresets.add(preset, aDouble);
            });
            presets.blueTrinketPresets = blueTrinkets.build();
            presets.redTrinketPresets = blueTrinkets.build();
            LEVELS.add(presets);
            return this;
        }

        @Override
        protected void configureConfig(RoyalePresetConfig config) {
            config.LEVELS.addAll(LEVELS);
            config.blueTrinketPresetSize = blueTrinketPresetSize;
            config.redTrinketPresetSize = redTrinketPresetSize;
            config.skillPresetSize = skillPresetSize;
        }

        public static class SkillPresetBuilder extends WeightedListBuilder<RoyalePresetConfig.SkillPreset> {
            public SkillPresetBuilder add(String name, int weight, Consumer<BasicListBuilder<RoyalePresetConfig.SkillEntry>> skillEntriesConsumer, Consumer<BasicListBuilder<RoyalePresetConfig.SkillEntry>> talentEntriesConsumer, Consumer<DescriptionDataBuilder> builderConsumer) {
                RoyalePresetConfig.SkillPreset preset = new RoyalePresetConfig.SkillPreset();
                BasicListBuilder<RoyalePresetConfig.SkillEntry> skillEntries = new BasicListBuilder<>();
                BasicListBuilder<RoyalePresetConfig.SkillEntry> talentEntries = new BasicListBuilder<>();
                DescriptionDataBuilder builder = new DescriptionDataBuilder();
                skillEntriesConsumer.accept(skillEntries);
                talentEntriesConsumer.accept(talentEntries);
                builderConsumer.accept(builder);

                preset.description = builder.build()[0];
                preset.abilities = skillEntries.build();
                preset.talents = talentEntries.build();
                preset.name = name;

                add(preset, weight);

                return this;
            }
        }
    }
}
