package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.vault.catalyst;

import iskallia.vault.config.CatalystConfig;
import iskallia.vault.config.tool.ToolPulverizingConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import org.checkerframework.checker.units.qual.C;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;

import java.util.HashMap;
import java.util.Map;

public class CatalystConfigLoader extends VaultConfigDataLoader<CatalystConfig> {
    public CatalystConfigLoader(String namespace) {
        super(new CatalystConfig(), "catalyst", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(CatalystConfig config : this.CUSTOM_CONFIGS.values()) {
            ModConfigs.CATALYST.pools.putAll(config.pools);
        }

    }
}
