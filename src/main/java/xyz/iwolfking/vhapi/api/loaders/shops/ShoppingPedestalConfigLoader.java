package xyz.iwolfking.vhapi.api.loaders.shops;

import iskallia.vault.config.ShopPedestalConfig;
import iskallia.vault.config.VaultCrystalConfig;
import iskallia.vault.config.entry.LevelEntryList;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.api.util.LevelEntryListHelper;

public class ShoppingPedestalConfigLoader extends VaultConfigProcessor<ShopPedestalConfig> {
    public ShoppingPedestalConfigLoader() {
        super(new ShopPedestalConfig(), "vendor_pedestal");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        this.CUSTOM_CONFIGS.forEach((resourceLocation, shopPedestalConfig) -> {
            LevelEntryListHelper.mergeList(
                    shopPedestalConfig.LEVELS,
                    ModConfigs.SHOP_PEDESTAL.LEVELS,
                    ShopPedestalConfig.ShopTier::getLevel,
                    (existing, srcEntry, levelList) -> {
                        existing.TRADE_POOL.putAll(srcEntry.TRADE_POOL);
                    }
            );
        });

        super.afterConfigsLoad(event);
    }

}
