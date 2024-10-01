package xyz.iwolfking.vaultcrackerlib.mixin.configs.gear;

import iskallia.vault.config.gear.VaultGearEnchantmentConfig;
import iskallia.vault.util.EnchantmentCost;
import iskallia.vault.util.EnchantmentEntry;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.Patchers;

import java.util.Map;

@Mixin(value = VaultGearEnchantmentConfig.class, remap = false)
public class MixinVaultGearEnchantmentConfig {
    @Shadow private Map<Enchantment, EnchantmentCost> costs;

    @Inject(method = "getCost", at = @At("HEAD"))
    private void addEnchantments(EnchantmentEntry entry, CallbackInfoReturnable<EnchantmentCost> cir) {
        Patchers.VAULT_GEAR_ENCHANTMENT_PATCHER.doPatches(this.costs);
    }
}
