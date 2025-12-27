package xyz.iwolfking.vhapi.api.loaders.misc;

import iskallia.vault.config.PreBuiltToolConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.PrebuiltToolConfigAccessor;

public class PrebuiltToolsConfigLoader extends VaultConfigProcessor<PreBuiltToolConfig> {
    public PrebuiltToolsConfigLoader() {
        super(new PreBuiltToolConfig(), "prebuilt_tools");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(PreBuiltToolConfig config : this.CUSTOM_CONFIGS.values()) {
            ((PrebuiltToolConfigAccessor)ModConfigs.PRE_BUILT_TOOLS).getTools().putAll(((PrebuiltToolConfigAccessor)config).getTools());
        }
        super.afterConfigsLoad(event);
    }
}
