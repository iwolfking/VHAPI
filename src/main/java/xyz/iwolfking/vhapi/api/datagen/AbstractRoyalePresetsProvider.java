package xyz.iwolfking.vhapi.api.datagen;

import com.google.gson.annotations.Expose;
import iskallia.vault.config.RoyalePresetConfig;
import iskallia.vault.config.TooltipConfig;
import iskallia.vault.config.entry.DescriptionData;
import iskallia.vault.config.entry.LevelEntryList;
import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.datagen.lib.BasicListBuilder;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.api.datagen.lib.WeightedListBuilder;
import xyz.iwolfking.vhapi.api.util.builder.description.DescriptionDataBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.TooltipConfigAccessor;

import javax.annotation.Nullable;
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

        public Builder add(int level, @Nullable Consumer<SkillPresetBuilder> builderConsumer, @Nullable Consumer<WeightedListBuilder<ResourceLocation>> redTrinketsConsumer, @Nullable Consumer<WeightedListBuilder<ResourceLocation>> blueTrinketsConsumer) {
            RoyalePresetConfig.Level presets = new RoyalePresetConfig.Level(level);

            if(builderConsumer != null) {
                SkillPresetBuilder builder = new SkillPresetBuilder();
                builderConsumer.accept(builder);
                builder.build().forEach((preset, aDouble) -> {
                    presets.skillPresets.add(preset, aDouble);
                });
            }

            if(redTrinketsConsumer != null) {
                WeightedListBuilder<ResourceLocation> redTrinkets = new WeightedListBuilder<>();
                redTrinketsConsumer.accept(redTrinkets);
                presets.redTrinketPresets = redTrinkets.build();
            }

            if(blueTrinketsConsumer != null) {
                WeightedListBuilder<ResourceLocation> blueTrinkets = new WeightedListBuilder<>();
                blueTrinketsConsumer.accept(blueTrinkets);
                presets.blueTrinketPresets = blueTrinkets.build();
            }

            LEVELS.add(presets);
            return this;
        }

        public Builder addSkillPreset(int level, Consumer<SkillPresetBuilder> builderConsumer) {
            return add(level, builderConsumer, null, null);
        }

        public Builder addRedTrinkets(int level, Consumer<WeightedListBuilder<ResourceLocation>> builderConsumer) {
            return add(level, null, builderConsumer, null);
        }

        public Builder addBlueTrinkets(int level, Consumer<WeightedListBuilder<ResourceLocation>> builderConsumer) {
            return add(level, null, null, builderConsumer);
        }

        @Override
        protected void configureConfig(RoyalePresetConfig config) {
            config.LEVELS.addAll(LEVELS);
            config.blueTrinketPresetSize = blueTrinketPresetSize;
            config.redTrinketPresetSize = redTrinketPresetSize;
            config.skillPresetSize = skillPresetSize;
        }

        public static class SkillPresetBuilder extends WeightedListBuilder<RoyalePresetConfig.SkillPreset> {
            public SkillPresetBuilder add(String name, int weight, Consumer<BasicListBuilder<RoyalePresetConfig.SkillEntry>> skillEntriesConsumer, Consumer<BasicListBuilder<RoyalePresetConfig.SkillEntry>> talentEntriesConsumer, TextComponent description) {
                RoyalePresetConfig.SkillPreset preset = new RoyalePresetConfig.SkillPreset();
                BasicListBuilder<RoyalePresetConfig.SkillEntry> skillEntries = new BasicListBuilder<>();
                BasicListBuilder<RoyalePresetConfig.SkillEntry> talentEntries = new BasicListBuilder<>();
                skillEntriesConsumer.accept(skillEntries);
                talentEntriesConsumer.accept(talentEntries);

                preset.description = DescriptionData.of(description);
                preset.abilities = skillEntries.build();
                preset.talents = talentEntries.build();
                preset.name = name;

                add(preset, weight);

                return this;
            }
        }
    }
}
