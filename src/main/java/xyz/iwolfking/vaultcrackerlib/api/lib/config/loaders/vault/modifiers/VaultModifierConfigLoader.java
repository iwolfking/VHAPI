package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.vault.modifiers;


import iskallia.vault.config.VaultModifiersConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.VaultModifierRegistyAccessor;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.VaultModifiersConfigAccessor;

import java.util.HashMap;

@Mod.EventBusSubscriber(modid = "vaultcrackerlib", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class VaultModifierConfigLoader extends VaultConfigDataLoader<VaultModifiersConfig> {

    public VaultModifierConfigLoader(String namespace) {
        super(new VaultModifiersConfig(), "vault/modifiers", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(VaultModifiersConfig config : this.CUSTOM_CONFIGS.values()) {
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

}
