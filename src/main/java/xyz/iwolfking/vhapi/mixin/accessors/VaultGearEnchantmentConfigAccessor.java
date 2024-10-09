package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.gear.VaultGearEnchantmentConfig;
import iskallia.vault.util.EnchantmentCost;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = VaultGearEnchantmentConfig.class, remap = false)
public interface VaultGearEnchantmentConfigAccessor {
    @Accessor("costs")
    public Map<Enchantment, EnchantmentCost> getCosts();
}
