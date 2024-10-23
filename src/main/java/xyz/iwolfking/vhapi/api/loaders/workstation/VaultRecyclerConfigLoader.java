package xyz.iwolfking.vhapi.api.loaders.workstation;

import xyz.iwolfking.vhapi.api.data.api.CustomRecyclerOutputs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.workstation.lib.CustomVaultRecyclerConfig;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

public class VaultRecyclerConfigLoader extends VaultConfigProcessor<CustomVaultRecyclerConfig> {
    public VaultRecyclerConfigLoader() {
        super(new CustomVaultRecyclerConfig(), "vault_recycler");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(CustomVaultRecyclerConfig config : this.CUSTOM_CONFIGS.values()) {
            CustomRecyclerOutputs.CUSTOM_OUTPUTS.putAll(config.getOutputs());
        }

    }
}
