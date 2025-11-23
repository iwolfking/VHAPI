package xyz.iwolfking.vhapi.api.loaders.vault;

import iskallia.vault.config.VaultItemsConfig;
import iskallia.vault.config.VaultMapRoomIconsConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

public class VaultItemsConfigLoader extends VaultConfigProcessor<VaultItemsConfig> {
    public VaultItemsConfigLoader() {
        super(new VaultItemsConfig(), "vault/items");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        this.CUSTOM_CONFIGS.forEach((resourceLocation, vaultItemsConfig) -> {
            if(vaultItemsConfig.VAULT_DOLL != null) {
                ModConfigs.VAULT_ITEMS.VAULT_DOLL = vaultItemsConfig.VAULT_DOLL;
            }
            else if(vaultItemsConfig.VAULT_COOKIE != null) {
                ModConfigs.VAULT_ITEMS.VAULT_COOKIE = vaultItemsConfig.VAULT_COOKIE;
            }
            else if(vaultItemsConfig.PLAIN_BURGER != null) {
                ModConfigs.VAULT_ITEMS.PLAIN_BURGER = vaultItemsConfig.PLAIN_BURGER;
            }
            else if(vaultItemsConfig.CHEESE_BURGER != null) {
                ModConfigs.VAULT_ITEMS.CHEESE_BURGER = vaultItemsConfig.CHEESE_BURGER;
            }
            else if(vaultItemsConfig.DOUBLE_CHEESE_BURGER != null) {
                ModConfigs.VAULT_ITEMS.DOUBLE_CHEESE_BURGER = vaultItemsConfig.DOUBLE_CHEESE_BURGER;
            }
            else if(vaultItemsConfig.DELUXE_CHEESE_BURGER != null) {
                ModConfigs.VAULT_ITEMS.DELUXE_CHEESE_BURGER = vaultItemsConfig.DELUXE_CHEESE_BURGER;
            }
            else if(vaultItemsConfig.CRISPY_DELUXE_CHEESE_BURGER != null) {
                ModConfigs.VAULT_ITEMS.CRISPY_DELUXE_CHEESE_BURGER = vaultItemsConfig.CRISPY_DELUXE_CHEESE_BURGER;
            }
            else if(vaultItemsConfig.SALTY_DELUXE_CHEESE_BURGER != null) {
                ModConfigs.VAULT_ITEMS.SALTY_DELUXE_CHEESE_BURGER = vaultItemsConfig.SALTY_DELUXE_CHEESE_BURGER;
            }
            else if(vaultItemsConfig.CHEESE_BURGER_FEAST != null) {
                ModConfigs.VAULT_ITEMS.CHEESE_BURGER_FEAST = vaultItemsConfig.CHEESE_BURGER_FEAST;
            }
            else if(vaultItemsConfig.SPICY_HEARTY_BURGER != null) {
                ModConfigs.VAULT_ITEMS.SPICY_HEARTY_BURGER = vaultItemsConfig.SPICY_HEARTY_BURGER;
            }
        });
        super.afterConfigsLoad(event);
    }
}
