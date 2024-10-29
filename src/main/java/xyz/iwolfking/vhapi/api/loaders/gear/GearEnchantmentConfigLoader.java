package xyz.iwolfking.vhapi.api.loaders.gear;

import iskallia.vault.config.gear.VaultGearEnchantmentConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.VaultGearEnchantmentConfigAccessor;

public class GearEnchantmentConfigLoader extends VaultConfigProcessor<VaultGearEnchantmentConfig> {
    public GearEnchantmentConfigLoader() {
        super(new VaultGearEnchantmentConfig(), "gear/enchantments");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(VaultGearEnchantmentConfig config : this.CUSTOM_CONFIGS.values()) {
            ((VaultGearEnchantmentConfigAccessor)ModConfigs.VAULT_GEAR_ENCHANTMENT_CONFIG).getCosts().putAll(((VaultGearEnchantmentConfigAccessor)config).getCosts());
        }
        super.afterConfigsLoad(event);
    }
}
