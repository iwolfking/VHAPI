package xyz.iwolfking.vhapi.api.lib.config.loaders.vault.mobs;

import iskallia.vault.config.VaultMobsConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vhapi.mixin.accessors.VaultMobsConfigAccessor;

import java.util.HashMap;

public class VaultMobsConfigLoader extends VaultConfigDataLoader<VaultMobsConfig> {
    public VaultMobsConfigLoader(String namespace) {
        super(new VaultMobsConfig(), "vault/mobs", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(VaultMobsConfig config : this.CUSTOM_CONFIGS.values()) {
            ((VaultMobsConfigAccessor)ModConfigs.VAULT_MOBS).getAttributeOverrides().putAll(((VaultMobsConfigAccessor)config).getAttributeOverrides());
        }

    }
}
