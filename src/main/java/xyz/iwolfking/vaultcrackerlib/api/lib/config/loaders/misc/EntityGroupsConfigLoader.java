package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.misc;

import iskallia.vault.config.EntityGroupsConfig;
import iskallia.vault.config.tool.ToolPulverizingConfig;
import iskallia.vault.core.world.data.entity.EntityPredicate;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;

import java.util.HashMap;
import java.util.Map;

public class EntityGroupsConfigLoader extends VaultConfigDataLoader<EntityGroupsConfig> {
    public EntityGroupsConfigLoader(String namespace) {
        super(new EntityGroupsConfig(), "entity_groups", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ResourceLocation key : this.CUSTOM_CONFIGS.keySet()) {
            EntityGroupsConfig config = CUSTOM_CONFIGS.get(key);

            if(key.getPath().contains("overwrite")) {
                ModConfigs.ENTITY_GROUPS = config;
            } else if (key.getPath().contains("remove")) {
                for(ResourceLocation entityGroupKey : config.getGroups().keySet()) {
                    if(ModConfigs.ENTITY_GROUPS.getGroups().containsKey(entityGroupKey)) {
                            ModConfigs.ENTITY_GROUPS.getGroups().get(entityGroupKey).removeAll(config.getGroups().get(entityGroupKey));
                    }
                }
            }
            else {
                for(ResourceLocation entityGroupKey : config.getGroups().keySet()) {
                    if(ModConfigs.ENTITY_GROUPS.getGroups().containsKey(entityGroupKey)) {
                        ModConfigs.ENTITY_GROUPS.getGroups().get(entityGroupKey).addAll(config.getGroups().get(entityGroupKey));
                    }
                    else {
                        ModConfigs.ENTITY_GROUPS.getGroups().put(entityGroupKey, config.getGroups().get(entityGroupKey));
                    }
                }
            }
        }

    }
}
