package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.vault.mobs;

import iskallia.vault.config.VaultMobsConfig;
import iskallia.vault.config.tool.ToolPulverizingConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.VaultMobsConfigAccessor;

import java.util.HashMap;
import java.util.Map;

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
