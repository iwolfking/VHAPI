package xyz.iwolfking.vhapi.api.loaders.gear;

import iskallia.vault.config.UniqueCodexConfig;
import iskallia.vault.config.UniqueDiscoveryConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.UniqueDiscoveryConfigAccessor;

public class UniqueDiscoveryConfigLoader extends VaultConfigProcessor<UniqueDiscoveryConfig> {
    public UniqueDiscoveryConfigLoader() {
        super(new UniqueDiscoveryConfig(), "unique_discovery");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ResourceLocation configKey : this.CUSTOM_CONFIGS.keySet()) {
            UniqueDiscoveryConfig config = this.CUSTOM_CONFIGS.get(configKey);
            ((UniqueDiscoveryConfigAccessor)ModConfigs.UNIQUE_DISCOVERY).getDiscoveryHints().putAll(((UniqueDiscoveryConfigAccessor)config).getDiscoveryHints());
        }
        super.afterConfigsLoad(event);
    }
}
