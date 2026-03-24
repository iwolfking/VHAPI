package xyz.iwolfking.vhapi.api.loaders.misc;

import iskallia.vault.config.GodShopConfig;
import iskallia.vault.config.VaultEntitiesConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.GodShopConfigAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.VaultEntitiesConfigAccessor;

public class
GodShopConfigLoader extends VaultConfigProcessor<GodShopConfig> {
    public GodShopConfigLoader() {
        super(new GodShopConfig(), "god_shop");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        CUSTOM_CONFIGS.forEach((resourceLocation, godShopConfig) -> {
            if(resourceLocation.getPath().equals("overwrite")) {
                ModConfigs.GOD_SHOP = godShopConfig;
            }
            else if(resourceLocation.getPath().equals("replace")) {
                ((GodShopConfigAccessor)ModConfigs.GOD_SHOP).getGodEntries().putAll(((GodShopConfigAccessor)godShopConfig).getGodEntries());
            }
            else {
                ((GodShopConfigAccessor)godShopConfig).getGodEntries().forEach((vaultGod, entries) -> {
                    if(((GodShopConfigAccessor)ModConfigs.GOD_SHOP).getGodEntries().containsKey(vaultGod)) {
                        ((GodShopConfigAccessor)ModConfigs.GOD_SHOP).getGodEntries().get(vaultGod).addAll(entries);
                    }
                    else {
                        ((GodShopConfigAccessor)ModConfigs.GOD_SHOP).getGodEntries().put(vaultGod, entries);
                    }
                });
            }
        });
        super.afterConfigsLoad(event);
    }
}
