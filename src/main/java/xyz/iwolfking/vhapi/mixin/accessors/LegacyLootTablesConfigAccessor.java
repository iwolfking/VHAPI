package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.LegacyLootTablesConfig;
import iskallia.vault.config.entry.LevelEntryList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = LegacyLootTablesConfig.class, remap = false)
public interface LegacyLootTablesConfigAccessor {
    @Accessor("LEVELS")
    public LevelEntryList<LegacyLootTablesConfig.Level> getLevels();
}
