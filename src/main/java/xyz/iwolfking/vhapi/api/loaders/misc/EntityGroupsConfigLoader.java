package xyz.iwolfking.vhapi.api.loaders.misc;

import iskallia.vault.config.EntityGroupsConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

public class EntityGroupsConfigLoader extends VaultConfigProcessor<EntityGroupsConfig> {
    public EntityGroupsConfigLoader() {
        super(new EntityGroupsConfig(), "entity_groups");
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
