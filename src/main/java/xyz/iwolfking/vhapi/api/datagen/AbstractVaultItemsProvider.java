package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.VaultItemsConfig;
import net.minecraft.data.DataGenerator;

public abstract class AbstractVaultItemsProvider extends AbstractVaultConfigDataProvider {
    protected AbstractVaultItemsProvider(DataGenerator generator, String modid) {
        super(generator, modid, "vault/items");
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Vault Items Provider";
    }

    public static class VaultItemsConfigBuilder {
        private final VaultItemsConfig itemsConfig = new VaultItemsConfig();

        public VaultItemsConfigBuilder configure(XPItem type, int minXp, int maxXp) {
            VaultItemsConfig.FlatExpFood food = new VaultItemsConfig.FlatExpFood(minXp, maxXp);
            switch (type) {
                case VAULT_COOKIE -> itemsConfig.VAULT_COOKIE = food;
                case PLAIN_BURGER -> itemsConfig.PLAIN_BURGER = food;
                case CHEESE_BURGER -> itemsConfig.CHEESE_BURGER = food;
                case DOUBLE_CHEESE_BURGER -> itemsConfig.DOUBLE_CHEESE_BURGER = food;
                case DELUXE_CHEESE_BURGER -> itemsConfig.DELUXE_CHEESE_BURGER = food;
                case CRISPY_DELUXE_CHEESE_BURGER -> itemsConfig.CRISPY_DELUXE_CHEESE_BURGER = food;
                case SALTY_DELUXE_CHEESE_BURGER -> itemsConfig.SALTY_DELUXE_CHEESE_BURGER = food;
                case CHEESE_BURGER_FEAST -> itemsConfig.CHEESE_BURGER_FEAST = food;
                case SPICY_HEARTY_BURGER -> itemsConfig.SPICY_HEARTY_BURGER = food;
            }
            return this;
        }

        public VaultItemsConfigBuilder configureDoll(float lootPercentageMin, float lootPercentageMax, float xpPercentageMin, float xpPercentageMax) {
            itemsConfig.VAULT_DOLL = new VaultItemsConfig.VaultDoll(lootPercentageMin, lootPercentageMax, xpPercentageMin, xpPercentageMax);
            return this;
        }

        public VaultItemsConfig build() {
            return itemsConfig;
        }

        public enum XPItem {
            VAULT_COOKIE,
            PLAIN_BURGER,
            CHEESE_BURGER,
            DOUBLE_CHEESE_BURGER,
            DELUXE_CHEESE_BURGER,
            CRISPY_DELUXE_CHEESE_BURGER,
            SALTY_DELUXE_CHEESE_BURGER,
            CHEESE_BURGER_FEAST,
            SPICY_HEARTY_BURGER
        }

    }
}
