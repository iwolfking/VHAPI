package xyz.iwolfking.vhapi.api.lib.config.loaders.shops;

import iskallia.vault.config.ShopPedestalConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.lib.loaders.VaultConfigDataLoader;

import java.util.HashMap;

public class ShoppingPedestalConfigLoader extends VaultConfigDataLoader<ShopPedestalConfig> {
    public ShoppingPedestalConfigLoader(String namespace) {
        super(new ShopPedestalConfig(), "vendor_pedestal", new HashMap<>(), namespace);
    }

    //Given that ShopTier is a private inner class, we are limited to overwriting the config for now, sadly.
    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ShopPedestalConfig config : this.CUSTOM_CONFIGS.values()) {
            ModConfigs.SHOP_PEDESTAL = config;
        }

    }
}
