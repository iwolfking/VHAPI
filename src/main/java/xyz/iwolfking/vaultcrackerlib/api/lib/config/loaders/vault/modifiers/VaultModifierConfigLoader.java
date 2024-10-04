package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.vault.modifiers;


import iskallia.vault.config.VaultModifiersConfig;
import iskallia.vault.core.vault.modifier.registry.VaultModifierRegistry;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.Loaders;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.VaultModifierRegistyAccessor;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.VaultModifiersConfigAccessor;

import java.util.HashMap;

@Mod.EventBusSubscriber(modid = "vaultcrackerlib", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class VaultModifierConfigLoader extends VaultConfigDataLoader<VaultModifiersConfig> {

    public static final VaultModifiersConfig instance = new VaultModifiersConfig();
    public VaultModifierConfigLoader(String namespace) {
        super(instance, "vault/modifiers", new HashMap<>(), namespace);
    }

    @SubscribeEvent
    public static void afterConfigsLoad(VaultConfigEvent.End event) {
        for(VaultModifiersConfig config : Loaders.VAULT_MODIFIER_CONFIG_LOADER.CUSTOM_CONFIGS.values()) {
            for(ResourceLocation key : ((VaultModifiersConfigAccessor)config).getModifierTypeGroups().keySet()) {
                if(VaultModifierRegistyAccessor.getVaultModifierMap() == null) {
                    return;
                }
                VaultModifierRegistyAccessor.getVaultModifierMap().putAll(((VaultModifiersConfigAccessor)config).getModifierTypeGroups().get(key));
                if(((VaultModifiersConfigAccessor)ModConfigs.VAULT_MODIFIERS).getModifierTypeGroups().containsKey(key)) {
                    ((VaultModifiersConfigAccessor) ModConfigs.VAULT_MODIFIERS).getModifierTypeGroups().get(key).putAll(((VaultModifiersConfigAccessor) config).getModifierTypeGroups().get(key));
                }
                else {
                    ((VaultModifiersConfigAccessor) ModConfigs.VAULT_MODIFIERS).getModifierTypeGroups().put(key, ((VaultModifiersConfigAccessor) config).getModifierTypeGroups().get(key));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onAddListeners(AddReloadListenerEvent event) {
        event.addListener(Loaders.VAULT_MODIFIER_CONFIG_LOADER);
    }
}
