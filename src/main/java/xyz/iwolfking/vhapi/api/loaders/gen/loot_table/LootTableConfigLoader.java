package xyz.iwolfking.vhapi.api.loaders.gen.loot_table;

import iskallia.vault.config.core.LootTablesConfig;
import iskallia.vault.core.Version;
import iskallia.vault.core.data.key.LootTableKey;
import iskallia.vault.core.data.key.registry.KeyRegistry;
import iskallia.vault.core.vault.VaultRegistry;
import iskallia.vault.core.world.loot.LootPool;
import iskallia.vault.core.world.loot.LootTable;
import iskallia.vault.core.world.loot.entry.LootEntry;
import xyz.iwolfking.vhapi.api.events.VaultGenConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.api.util.ResourceLocUtils;
import xyz.iwolfking.vhapi.mixin.accessors.KeyRegistryAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.KeyRegistryConfigAccessor;

public class LootTableConfigLoader extends VaultConfigProcessor<LootTablesConfig> {
    public LootTableConfigLoader() {
        super(new LootTablesConfig(), "vault_loot_tables");
    }

    @Override
    public void afterGenConfigsRegistriesBuilt(VaultGenConfigEvent.RegistriesBuilt event) {
        ((KeyRegistryAccessor) VaultRegistry.LOOT_TABLE).setLocked(false);
        for(LootTablesConfig config : this.CUSTOM_CONFIGS.values()) {
            for(LootTableKey key : toRegistry(config).getKeys()) {
                if(key.getId().getPath().endsWith("_merge")) {
                    LootTableKey existingKey = VaultRegistry.LOOT_TABLE.getKey(ResourceLocUtils.removeSuffixFromId("_merge", key.getId()));
                    LootTable existingPool = existingKey.get(Version.latest());
                    LootTable mergePool = key.get(Version.v1_0);
                    for(int i = 0; i < mergePool.getEntries().size(); i++) {
                        if(existingPool.getEntries().get(i) != null) {
                            LootPool pool = mergePools(existingPool.getEntries().get(i).getPool(), mergePool.getEntries().get(i).getPool());
                            existingPool.getEntries().set(i, new LootTable.Entry(existingPool.getEntries().get(i).getRoll(), pool));
                        }
                        else {
                            existingPool.getEntries().add(mergePool.getEntries().get(i));
                        }
                    }

                    VaultRegistry.LOOT_TABLE.register(existingKey.with(Version.latest(), existingPool));
                }
                else {
                    VaultRegistry.LOOT_TABLE.register(key.with(Version.latest(), key.get(Version.v1_0)));
                }
            }
        }
        ((KeyRegistryAccessor)VaultRegistry.LOOT_TABLE).setLocked(true);
        super.afterGenConfigsRegistriesBuilt(event);
    }

    public KeyRegistry<LootTableKey, LootTable, Version> toRegistry(LootTablesConfig config) {
        KeyRegistry<LootTableKey, LootTable, Version> registry = config.create();
        ((KeyRegistryConfigAccessor<LootTableKey>)config).getKeys().forEach(registry::register);
        return registry;
    }

    public static LootPool mergePools(LootPool a, LootPool b) {
        LootPool merged = new LootPool();

        copyPool(a, merged);
        copyPool(b, merged);

        return merged;
    }

    private static void copyPool(LootPool from, LootPool to) {
        from.getChildren().forEach((entry, weight) -> {

            if (entry instanceof LootEntry lootEntry) {
                to.addLeaf(lootEntry, weight);

            } else if (entry instanceof LootPool nestedPool) {
                to.addTree(nestedPool, weight);

            } else {
                throw new IllegalStateException(
                        "Unknown weighted entry: " + entry.getClass());
            }
        });
    }




}
