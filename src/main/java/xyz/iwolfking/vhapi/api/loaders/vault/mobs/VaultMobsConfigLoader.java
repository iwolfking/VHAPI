package xyz.iwolfking.vhapi.api.loaders.vault.mobs;

import iskallia.vault.config.VaultMobsConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.VaultMobsConfigAccessor;

public class VaultMobsConfigLoader extends VaultConfigProcessor<VaultMobsConfig> {
    public VaultMobsConfigLoader() {
        super(new VaultMobsConfig(), "vault/mobs");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(VaultMobsConfig config : this.CUSTOM_CONFIGS.values()) {
            ((VaultMobsConfigAccessor)ModConfigs.VAULT_MOBS).getAttributeOverrides().putAll(((VaultMobsConfigAccessor)config).getAttributeOverrides());
        }

    }
}
