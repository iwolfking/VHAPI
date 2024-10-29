package xyz.iwolfking.vhapi.api.loaders.loot;

import iskallia.vault.config.LegacyLootTablesConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;

import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.LegacyLootTablesConfigAccessor;

import java.util.HashMap;
import java.util.Map;

public class LegacyLootTableConfigLoader extends VaultConfigProcessor<LegacyLootTablesConfig> {
    public LegacyLootTableConfigLoader() {
        super(new LegacyLootTablesConfig(), "legacy_loot_tables");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ResourceLocation key : this.CUSTOM_CONFIGS.keySet()) {
            LegacyLootTablesConfig config = CUSTOM_CONFIGS.get(key);
            if(!key.getPath().contains("new")) {
                Map<Integer, Map<String, String>> completionsToAdd = new HashMap<>();
                for(LegacyLootTablesConfig.Level level : ((LegacyLootTablesConfigAccessor)config).getLevels()) {
                    completionsToAdd.put(level.getLevel(), level.COMPLETION_CRATE);
                }

                for(LegacyLootTablesConfig.Level level : ((LegacyLootTablesConfigAccessor)ModConfigs.LOOT_TABLES).getLevels()) {
                    level.COMPLETION_CRATE.putAll(completionsToAdd.get(level.getLevel()));
                }
            }
            else {
                ((LegacyLootTablesConfigAccessor)ModConfigs.LOOT_TABLES).getLevels().addAll(((LegacyLootTablesConfigAccessor)config).getLevels());
            }

        }
        super.afterConfigsLoad(event);
    }
}
