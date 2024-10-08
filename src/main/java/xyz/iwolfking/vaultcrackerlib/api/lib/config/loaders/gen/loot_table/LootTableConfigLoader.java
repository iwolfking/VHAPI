package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gen.loot_table;

import iskallia.vault.config.core.LootTablesConfig;
import iskallia.vault.config.core.PalettesConfig;
import iskallia.vault.core.Version;
import iskallia.vault.core.data.key.LootTableKey;
import iskallia.vault.core.data.key.PaletteKey;
import iskallia.vault.core.vault.VaultRegistry;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.KeyRegistryAccessor;

import java.util.HashMap;

public class LootTableConfigLoader extends VaultConfigDataLoader<LootTablesConfig> {
    public LootTableConfigLoader(String namespace) {
        super(new LootTablesConfig(), "loot_tables", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        ((KeyRegistryAccessor) VaultRegistry.LOOT_TABLE).setLocked(false);
        for(LootTablesConfig config : this.CUSTOM_CONFIGS.values()) {
            for(LootTableKey key : config.toRegistry().getKeys()) {
                VaultRegistry.LOOT_TABLE.register(key.with(Version.latest(), key.get(Version.v1_0)));
            }
        }
        ((KeyRegistryAccessor)VaultRegistry.LOOT_TABLE).setLocked(true);
    }
}
