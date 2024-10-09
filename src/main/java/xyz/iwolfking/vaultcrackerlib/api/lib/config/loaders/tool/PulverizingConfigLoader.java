package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.tool;

import iskallia.vault.config.recipe.CatalystRecipesConfig;
import iskallia.vault.config.tool.ToolPulverizingConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;

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
