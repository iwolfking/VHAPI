package xyz.iwolfking.vhapi.api.loaders.misc;

import iskallia.vault.config.CustomEntitySpawnerConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

public class CustomEntitySpawnersLoader extends VaultConfigProcessor<CustomEntitySpawnerConfig> {
    public CustomEntitySpawnersLoader() {
        super(new CustomEntitySpawnerConfig(), "custom_entity_spawners");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(CustomEntitySpawnerConfig config : this.CUSTOM_CONFIGS.values()) {
            ModConfigs.CUSTOM_ENTITY_SPAWNER.spawnerGroups.putAll(config.spawnerGroups);
        }

    }
}
