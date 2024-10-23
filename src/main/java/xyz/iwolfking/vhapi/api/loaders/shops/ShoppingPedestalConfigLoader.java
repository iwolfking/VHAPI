package xyz.iwolfking.vhapi.api.loaders.shops;

import iskallia.vault.config.ShopPedestalConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

public class ShoppingPedestalConfigLoader extends VaultConfigProcessor<ShopPedestalConfig> {
    public ShoppingPedestalConfigLoader() {
        super(new ShopPedestalConfig(), "vendor_pedestal");
    }

    //Given that ShopTier is a private inner class, we are limited to overwriting the config for now, sadly.
    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ShopPedestalConfig config : this.CUSTOM_CONFIGS.values()) {
            ModConfigs.SHOP_PEDESTAL = config;
        }

    }
}
