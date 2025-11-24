package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.VaultCrystalConfig;
import iskallia.vault.config.entry.LevelEntryList;
import iskallia.vault.core.vault.modifier.VaultModifierStack;
import iskallia.vault.item.crystal.layout.CrystalLayout;
import iskallia.vault.item.crystal.modifiers.CrystalModifiers;
import iskallia.vault.item.crystal.modifiers.DefaultCrystalModifiers;
import iskallia.vault.item.crystal.objective.CrystalObjective;
import iskallia.vault.item.crystal.theme.CrystalTheme;
import iskallia.vault.item.crystal.time.CrystalTime;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.datagen.lib.WeightedLevelEntryListBuilder;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.VaultCrystalConfigAccessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractVaultCrystalConfigProvider extends AbstractVaultConfigDataProvider<AbstractVaultCrystalConfigProvider.Builder> {
    protected AbstractVaultCrystalConfigProvider(DataGenerator generator, String modid) {
        super(generator, modid, "vault/crystal", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Tooltip Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<VaultCrystalConfig> {

        Map<ResourceLocation, LevelEntryList<VaultCrystalConfig.ObjectiveEntry>> objectivesMap = new HashMap<>();
        Map<ResourceLocation, LevelEntryList<VaultCrystalConfig.SealEntry>> sealsMap = new HashMap<>();

        public Builder() {
            super(VaultCrystalConfig::new);
        }

        public Builder addObjective(ResourceLocation id, Consumer<WeightedLevelEntryListBuilder<VaultCrystalConfig.ObjectiveEntry, CrystalObjective>> levelEntryListBuilderConsumer) {
            WeightedLevelEntryListBuilder<VaultCrystalConfig.ObjectiveEntry, CrystalObjective> builder = new WeightedLevelEntryListBuilder<>(VaultCrystalConfig.ObjectiveEntry::new);
            levelEntryListBuilderConsumer.accept(builder);
            objectivesMap.put(id, builder.build());
            return this;
        }

        @Override
        protected void configureConfig(VaultCrystalConfig config) {
            ((VaultCrystalConfigAccessor)config).setObjectives(objectivesMap);
        }

        public static class SealEntryBuilder {
            private final int level;
            private final List<ResourceLocation> input = new ArrayList<>();
            private CrystalObjective objective;
            private CrystalLayout layout;
            private CrystalTheme theme;
            private CrystalTime time;
            private final CrystalModifiers modifiers = new DefaultCrystalModifiers();
            private Boolean exhausted;

            public SealEntryBuilder(int level) {
                this.level = level;
            }

            public SealEntryBuilder input(ResourceLocation id) {
                input.add(id);
                return this;
            }

            public SealEntryBuilder objective(CrystalObjective objective) {
                this.objective = objective;
                return this;
            }

            public SealEntryBuilder layout(CrystalLayout layout) {
                this.layout = layout;
                return this;
            }

            public SealEntryBuilder theme(CrystalTheme theme) {
                this.theme = theme;
                return this;
            }

            public SealEntryBuilder time(CrystalTime time) {
                this.time = time;
                return this;
            }

            public SealEntryBuilder modifier(VaultModifierStack modifier) {
                modifiers.add(modifier);
                return this;
            }

            public SealEntryBuilder exhausted(boolean exhausted) {
                this.exhausted = exhausted;
                return this;
            }


            public VaultCrystalConfig.SealEntry build() {
                return new VaultCrystalConfig.SealEntry(level, input, objective, layout, theme, time, modifiers, exhausted);
            }
        }
    }
}
