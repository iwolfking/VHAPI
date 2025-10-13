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

    //TODO: Fix this - was changed in VH 19.6, supports overwriting the config only now.
    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ResourceLocation key : this.CUSTOM_CONFIGS.keySet()) {
            EntityGroupsConfig config = CUSTOM_CONFIGS.get(key);

            if (key.getPath().contains("overwrite")) {
                ModConfigs.ENTITY_GROUPS = config;
            }
            super.afterConfigsLoad(event);
        }
    }
}
