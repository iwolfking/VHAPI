package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.loot;

import iskallia.vault.config.LegacyLootTablesConfig;
import iskallia.vault.config.tool.ToolPulverizingConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import org.checkerframework.checker.units.qual.C;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.LegacyLootTablesConfigAccessor;

import java.util.HashMap;
import java.util.Map;

public class LegacyLootTableConfigLoader extends VaultConfigDataLoader<LegacyLootTablesConfig> {
    public LegacyLootTableConfigLoader(String namespace) {
        super(new LegacyLootTablesConfig(), "tool/pulverizing", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(LegacyLootTablesConfig config : this.CUSTOM_CONFIGS.values()) {
            Map<Integer, Map<String, String>> completionsToAdd = new HashMap<>();
            for(LegacyLootTablesConfig.Level level : ((LegacyLootTablesConfigAccessor)config).getLevels()) {
                completionsToAdd.put(level.getLevel(), level.COMPLETION_CRATE);
            }

            for(LegacyLootTablesConfig.Level level : ((LegacyLootTablesConfigAccessor)ModConfigs.LOOT_TABLES).getLevels()) {
                level.COMPLETION_CRATE.putAll(completionsToAdd.get(level.getLevel()));
            }
        }

    }
}
