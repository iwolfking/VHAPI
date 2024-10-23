package xyz.iwolfking.vhapi.api.loaders.vault.modifiers;


import iskallia.vault.config.VaultModifiersConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.VaultModifierRegistyAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.VaultModifiersConfigAccessor;

@Mod.EventBusSubscriber(modid = "vhapi", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class VaultModifierConfigLoader extends VaultConfigProcessor<VaultModifiersConfig> {

    public VaultModifierConfigLoader() {
        super(new VaultModifiersConfig(), "vault/modifiers");
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
