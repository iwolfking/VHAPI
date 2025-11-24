package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.LegacyLootTablesConfig;
import iskallia.vault.config.entry.LevelEntryList;
import net.minecraft.data.DataGenerator;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.LegacyLootTablesConfigAccessor;

import java.util.function.Supplier;

public abstract class AbstractLegacyLootTableProvider extends AbstractVaultConfigDataProvider<AbstractLegacyLootTableProvider.Builder> {
    protected AbstractLegacyLootTableProvider(DataGenerator generator, String modid) {
        super(generator, modid, "legacy_loot_tables", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Legacy Loot Tables Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<LegacyLootTablesConfig> {
        LevelEntryList<LegacyLootTablesConfig.Level> LEVELS = new LevelEntryList<>();

        public Builder() {
            super(LegacyLootTablesConfig::new);
        }

        public Builder addLevel(LegacyLootTablesConfig.Level level) {
            LEVELS.add(level);
            return this;
        }

        @Override
        protected void configureConfig(LegacyLootTablesConfig config) {
            ((LegacyLootTablesConfigAccessor)config).getLevels().addAll(LEVELS);
        }
    }
}
