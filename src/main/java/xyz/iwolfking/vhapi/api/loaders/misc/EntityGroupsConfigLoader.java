package xyz.iwolfking.vhapi.api.loaders.misc;

import iskallia.vault.config.EntityGroupsConfig;
import iskallia.vault.core.world.data.entity.EntityPredicate;
import iskallia.vault.core.world.data.entity.OrEntityPredicate;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

import java.util.Arrays;
import java.util.List;

public class EntityGroupsConfigLoader extends VaultConfigProcessor<EntityGroupsConfig> {
    public EntityGroupsConfigLoader() {
        super(new EntityGroupsConfig(), "entity_groups");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ResourceLocation key : this.CUSTOM_CONFIGS.keySet()) {
            EntityGroupsConfig config = CUSTOM_CONFIGS.get(key);


            if (key.getPath().contains("overwrite")) {
                ModConfigs.ENTITY_GROUPS = config;
            }
            else {
                config.getGroups().forEach((resourceLocation, entityPredicate) -> {
                    if(ModConfigs.ENTITY_GROUPS.getGroups().containsKey(resourceLocation)) {
                        List<EntityPredicate> entityPredicateList = new java.util.ArrayList<>(Arrays.stream(((OrEntityPredicate) ModConfigs.ENTITY_GROUPS.getGroups().get(resourceLocation)).getChildren()).toList());
                        entityPredicateList.add(entityPredicate);
                        ModConfigs.ENTITY_GROUPS.getGroups().put(resourceLocation, new OrEntityPredicate(entityPredicateList.toArray(new EntityPredicate[]{})));
                    }
                    else {
                        ModConfigs.ENTITY_GROUPS.getGroups().put(resourceLocation, entityPredicate);
                    }
                });
            }

            super.afterConfigsLoad(event);
        }
    }
}
