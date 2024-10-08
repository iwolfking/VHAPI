package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear;

import iskallia.vault.config.TrinketConfig;
import iskallia.vault.config.gear.VaultGearEnchantmentConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.VaultGearEnchantmentConfigAccessor;

import java.util.HashMap;
import java.util.Map;

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
