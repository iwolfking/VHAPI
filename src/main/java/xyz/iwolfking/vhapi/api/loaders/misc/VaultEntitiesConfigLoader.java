package xyz.iwolfking.vhapi.api.loaders.misc;

import iskallia.vault.config.CustomEntitySpawnerConfig;
import iskallia.vault.config.VaultEntitiesConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.VaultEntitiesConfigAccessor;

public class
VaultEntitiesConfigLoader extends VaultConfigProcessor<VaultEntitiesConfig> {
    public VaultEntitiesConfigLoader() {
        super(new VaultEntitiesConfig(), "vault_entities");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(VaultEntitiesConfig config : this.CUSTOM_CONFIGS.values()) {
            ((VaultEntitiesConfigAccessor)ModConfigs.VAULT_ENTITIES).getDeathEffects().addAll(((VaultEntitiesConfigAccessor)config).getDeathEffects());
            ((VaultEntitiesConfigAccessor)ModConfigs.VAULT_ENTITIES).getThrowEffects().addAll(((VaultEntitiesConfigAccessor)config).getThrowEffects());
        }
        super.afterConfigsLoad(event);
    }
}
