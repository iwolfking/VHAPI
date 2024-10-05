package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.vault.modifiers;

import iskallia.vault.config.VaultModifierPoolsConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraftforge.fml.common.Mod;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;


import java.util.HashMap;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = "vaultcrackerlib", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class VaultModifierPoolsConfigLoader extends VaultConfigDataLoader<VaultModifierPoolsConfig> {

    public static final VaultModifierPoolsConfig instance = new VaultModifierPoolsConfig();
    public VaultModifierPoolsConfigLoader(String namespace) {
        super(instance, "vault/modifier_pools", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(VaultModifierPoolsConfig config : this.CUSTOM_CONFIGS.values()) {
            ModConfigs.VAULT_MODIFIER_POOLS.pools.putAll(config.pools);
        }
    }
}
