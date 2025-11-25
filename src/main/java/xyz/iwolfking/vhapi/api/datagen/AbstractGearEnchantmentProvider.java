package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.TooltipConfig;
import iskallia.vault.config.gear.VaultGearEnchantmentConfig;
import iskallia.vault.init.ModItems;
import iskallia.vault.util.EnchantmentCost;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.TooltipConfigAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.VaultGearEnchantmentConfigAccessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractGearEnchantmentProvider extends AbstractVaultConfigDataProvider<AbstractGearEnchantmentProvider.Builder> {
    protected AbstractGearEnchantmentProvider(DataGenerator generator, String modid) {
        super(generator, modid, "gear/enchantments", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Gear Enchantment Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<VaultGearEnchantmentConfig> {
        private final Map<Enchantment, EnchantmentCost> costsMap = new HashMap<>();

        public Builder() {
            super(VaultGearEnchantmentConfig::new);
        }

        public Builder addEnchantment(ResourceLocation enchantmentId, List<ItemStack> costsList, int levelCost) {
            costsMap.put(ForgeRegistries.ENCHANTMENTS.getValue(enchantmentId), new EnchantmentCost(costsList, levelCost));
            return this;
        }

        public Builder addEnchantment(Enchantment enchantment, List<ItemStack> costsList, int levelCost) {
            costsMap.put(enchantment, new EnchantmentCost(costsList, levelCost));
            return this;
        }

        public Builder addEnchantment(ResourceLocation enchantmentId, int emeraldCost, int levelCost) {
            return addEnchantment(enchantmentId, List.of(new ItemStack(Items.EMERALD, emeraldCost)), levelCost);
        }

        public Builder addEnchantment(Enchantment enchantmentId, int emeraldCost, int levelCost) {
            return addEnchantment(enchantmentId, List.of(new ItemStack(Items.EMERALD, emeraldCost)), levelCost);
        }

        public Builder addEnchantment(ResourceLocation enchantmentId, Consumer<List<ItemStack>> costsConsumer, int levelCost) {
            List<ItemStack> costs = new ArrayList<>();
            costsConsumer.accept(costs);
            return addEnchantment(enchantmentId, costs, levelCost);
        }

        public Builder addEnchantment(Enchantment enchantment, Consumer<List<ItemStack>> costsConsumer, int levelCost) {
            return addEnchantment(enchantment.getRegistryName(), costsConsumer, levelCost);
        }

        @Override
        protected void configureConfig(VaultGearEnchantmentConfig config) {
            ((VaultGearEnchantmentConfigAccessor)config).getCosts().putAll(costsMap);
        }
    }
}
