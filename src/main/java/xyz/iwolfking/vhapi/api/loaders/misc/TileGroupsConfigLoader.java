package xyz.iwolfking.vhapi.api.loaders.misc;

import iskallia.vault.config.EntityGroupsConfig;
import iskallia.vault.config.TileGroupsConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

public class TileGroupsConfigLoader extends VaultConfigProcessor<TileGroupsConfig> {
    public TileGroupsConfigLoader() {
        super(new TileGroupsConfig(), "tile_groups");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ResourceLocation key : this.CUSTOM_CONFIGS.keySet()) {
            TileGroupsConfig config = CUSTOM_CONFIGS.get(key);

            if(key.getPath().contains("overwrite")) {
                ModConfigs.TILE_GROUPS = config;
            } else if (key.getPath().contains("remove")) {
                for(ResourceLocation entityGroupKey : config.getGroups().keySet()) {
                    if(ModConfigs.TILE_GROUPS.getGroups().containsKey(entityGroupKey)) {
                        ModConfigs.TILE_GROUPS.getGroups().get(entityGroupKey).removeAll(config.getGroups().get(entityGroupKey));
                    }
                }
            }
            else {
                for(ResourceLocation entityGroupKey : config.getGroups().keySet()) {
                    if(ModConfigs.TILE_GROUPS.getGroups().containsKey(entityGroupKey)) {
                        ModConfigs.TILE_GROUPS.getGroups().get(entityGroupKey).addAll(config.getGroups().get(entityGroupKey));
                    }
                    else {
                        ModConfigs.TILE_GROUPS.getGroups().put(entityGroupKey, config.getGroups().get(entityGroupKey));
                    }
                }
            }
        }
        super.afterConfigsLoad(event);
    }
}
