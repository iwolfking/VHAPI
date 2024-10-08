package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.shops;

import iskallia.vault.config.ShopPedestalConfig;
import iskallia.vault.config.tool.ToolPulverizingConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;

import java.util.HashMap;
import java.util.Map;

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
