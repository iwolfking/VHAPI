package xyz.iwolfking.vhapi.api.lib.config.loaders.tool;

import iskallia.vault.config.tool.ToolPulverizingConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.lib.loaders.VaultConfigDataLoader;

import java.util.HashMap;

public class PulverizingConfigLoader extends VaultConfigDataLoader<ToolPulverizingConfig> {
    public PulverizingConfigLoader(String namespace) {
        super(new ToolPulverizingConfig(), "tool/pulverizing", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ToolPulverizingConfig config : this.CUSTOM_CONFIGS.values()) {
            ModConfigs.TOOL_PULVERIZING.getLoot().putAll(config.getLoot());
        }

    }
}
