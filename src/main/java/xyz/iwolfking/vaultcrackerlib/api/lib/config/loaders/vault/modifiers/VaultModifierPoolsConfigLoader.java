package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.vault.modifiers;

import iskallia.vault.config.VaultModifierPoolsConfig;
import iskallia.vault.core.vault.modifier.registry.VaultModifierRegistry;
import iskallia.vault.init.ModConfigs;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.Loaders;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.VaultModifierRegistyAccessor;


import java.util.HashMap;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = "vaultcrackerlib", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class VaultModifierPoolsConfigLoader extends VaultConfigDataLoader<VaultModifierPoolsConfig> {

    public static final VaultModifierPoolsConfig instance = new VaultModifierPoolsConfig();
    public VaultModifierPoolsConfigLoader(String namespace) {
        super(instance, "vault/modifier_pools", new HashMap<>(), namespace);
    }

    @SubscribeEvent
    public static void afterConfigsLoad(VaultConfigEvent.End event) {
        for(VaultModifierPoolsConfig config : Loaders.VAULT_MODIFIER_POOLS_CONFIG_LOADER.CUSTOM_CONFIGS.values()) {
            ModConfigs.VAULT_MODIFIER_POOLS.pools.putAll(config.pools);
        }
    }

    @SubscribeEvent
    public static void onAddListeners(AddReloadListenerEvent event) {
        event.addListener(Loaders.VAULT_MODIFIER_POOLS_CONFIG_LOADER);
    }
}
