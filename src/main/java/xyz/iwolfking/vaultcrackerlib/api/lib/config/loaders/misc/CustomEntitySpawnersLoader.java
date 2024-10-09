package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.misc;

import iskallia.vault.config.CustomEntitySpawnerConfig;
import iskallia.vault.config.tool.ToolPulverizingConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;

import java.util.HashMap;

public class CustomEntitySpawnersLoader extends VaultConfigDataLoader<CustomEntitySpawnerConfig> {
    public CustomEntitySpawnersLoader(String namespace) {
        super(new CustomEntitySpawnerConfig(), "custom_entity_spawners", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(CustomEntitySpawnerConfig config : this.CUSTOM_CONFIGS.values()) {
            ModConfigs.CUSTOM_ENTITY_SPAWNER.spawnerGroups.putAll(config.spawnerGroups);
        }

    }
}
