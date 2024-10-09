package xyz.iwolfking.vhapi.api.lib.config.loaders.gear;

import iskallia.vault.config.gear.VaultGearEnchantmentConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vhapi.mixin.accessors.VaultGearEnchantmentConfigAccessor;

import java.util.HashMap;

public class GearEnchantmentConfigLoader extends VaultConfigDataLoader<VaultGearEnchantmentConfig> {
    public GearEnchantmentConfigLoader(String namespace) {
        super(new VaultGearEnchantmentConfig(), "gear/enchantments", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(VaultGearEnchantmentConfig config : this.CUSTOM_CONFIGS.values()) {
            ((VaultGearEnchantmentConfigAccessor)ModConfigs.VAULT_GEAR_ENCHANTMENT_CONFIG).getCosts().putAll(((VaultGearEnchantmentConfigAccessor)config).getCosts());
        }
    }
}
