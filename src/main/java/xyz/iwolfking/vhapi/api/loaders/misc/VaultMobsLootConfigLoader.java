package xyz.iwolfking.vhapi.api.loaders.misc;

import iskallia.vault.config.GodShopConfig;
import iskallia.vault.config.VaultMobLoot;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.GodShopConfigAccessor;

public class
VaultMobsLootConfigLoader extends VaultConfigProcessor<VaultMobLoot> {
    public VaultMobsLootConfigLoader() {
        super(new VaultMobLoot(), "vault/mob_loot");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        CUSTOM_CONFIGS.forEach((resourceLocation, vaultMobLoot) -> {
            if(resourceLocation.getPath().contains("overwrite")) {
                ModConfigs.VAULT_MOBS_LOOT = vaultMobLoot;
            }
            else if(resourceLocation.getPath().contains("remove")) {
                vaultMobLoot.getLootMap().forEach((entityPredicate, resourceLocation1) -> {
                    ModConfigs.VAULT_MOBS_LOOT.getLootMap().remove(entityPredicate);
                });
            }
            else {
                ModConfigs.VAULT_MOBS_LOOT.getLootMap().putAll(vaultMobLoot.getLootMap());
            }
        });
        super.afterConfigsLoad(event);
    }
}
