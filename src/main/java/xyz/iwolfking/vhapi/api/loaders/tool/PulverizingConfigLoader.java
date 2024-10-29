package xyz.iwolfking.vhapi.api.loaders.tool;

import iskallia.vault.config.tool.ToolPulverizingConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

public class PulverizingConfigLoader extends VaultConfigProcessor<ToolPulverizingConfig> {
    public PulverizingConfigLoader() {
        super(new ToolPulverizingConfig(), "tool/pulverizing");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ToolPulverizingConfig config : this.CUSTOM_CONFIGS.values()) {
            ModConfigs.TOOL_PULVERIZING.getLoot().putAll(config.getLoot());
        }
        super.afterConfigsLoad(event);
    }
}
