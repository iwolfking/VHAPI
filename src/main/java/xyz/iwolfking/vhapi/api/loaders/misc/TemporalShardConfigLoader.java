package xyz.iwolfking.vhapi.api.loaders.misc;

import iskallia.vault.config.TemporalShardConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.TemporalShardConfigAccessor;

public class
TemporalShardConfigLoader extends VaultConfigProcessor<TemporalShardConfig> {
    public TemporalShardConfigLoader() {
        super(new TemporalShardConfig(), "temporal_shard");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(TemporalShardConfig config : this.CUSTOM_CONFIGS.values()) {
            ((TemporalShardConfigAccessor)ModConfigs.TEMPORAL_SHARD).getModifiers().addAll(((TemporalShardConfigAccessor)config).getModifiers());
        }
        super.afterConfigsLoad(event);
    }
}
