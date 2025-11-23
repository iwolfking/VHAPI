package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.LegacyLootTablesConfig;
import iskallia.vault.config.TooltipConfig;
import iskallia.vault.config.entry.LevelEntryList;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.mixin.accessors.LegacyLootTablesConfigAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.TooltipConfigAccessor;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractLegacyLootTableProvider extends AbstractVaultConfigDataProvider {
    protected AbstractLegacyLootTableProvider(DataGenerator generator, String modid) {
        super(generator, modid, "legacy_loot_tables");
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Legacy Loot Tables Data Provider";
    }

    public static class LegacyLootTablesConfigBuilder {
        LevelEntryList<LegacyLootTablesConfig.Level> LEVELS = new LevelEntryList<>();

        public LegacyLootTablesConfigBuilder addLevel(LegacyLootTablesConfig.Level level) {
            LEVELS.add(level);
            return this;
        }

        public LegacyLootTablesConfig build() {
            LegacyLootTablesConfig newConfig = new LegacyLootTablesConfig();
            ((LegacyLootTablesConfigAccessor)newConfig).getLevels().addAll(LEVELS);
            return newConfig;
        }

    }
}
