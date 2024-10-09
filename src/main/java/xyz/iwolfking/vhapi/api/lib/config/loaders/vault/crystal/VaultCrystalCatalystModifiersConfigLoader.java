package xyz.iwolfking.vhapi.api.lib.config.loaders.vault.crystal;

import iskallia.vault.config.VaultCrystalCatalystConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vhapi.mixin.accessors.ModifierPoolAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.VaultCrystalCatalystConfigAccessor;

import java.util.HashMap;

public class VaultCrystalCatalystModifiersConfigLoader extends VaultConfigDataLoader<VaultCrystalCatalystConfig> {
    public VaultCrystalCatalystModifiersConfigLoader(String namespace) {
        super(new VaultCrystalCatalystConfig(), "vault/crystal_catalyst_modifiers", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ResourceLocation key : this.CUSTOM_CONFIGS.keySet()) {
            VaultCrystalCatalystConfig config = CUSTOM_CONFIGS.get(key);
            if(key.getPath().contains("overwrite")) {
                ModConfigs.VAULT_CRYSTAL_CATALYST = config;
            }
            else {
                for(String groupKey : ((VaultCrystalCatalystConfigAccessor)config).getModifierPools().keySet()) {
                    if(((VaultCrystalCatalystConfigAccessor)ModConfigs.VAULT_CRYSTAL_CATALYST).getModifierPools().containsKey(groupKey)) {
                        ((ModifierPoolAccessor)((VaultCrystalCatalystConfigAccessor) ModConfigs.VAULT_CRYSTAL_CATALYST).getModifierPools().get(groupKey)).getModifierIds().addAll(((ModifierPoolAccessor)((VaultCrystalCatalystConfigAccessor) config).getModifierPools().get(groupKey)).getModifierIds());
                    }
                }
            }

        }

    }
}
