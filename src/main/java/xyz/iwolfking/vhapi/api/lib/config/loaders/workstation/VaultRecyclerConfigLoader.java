package xyz.iwolfking.vhapi.api.lib.config.loaders.workstation;

import xyz.iwolfking.vhapi.api.data.CustomRecyclerOutputs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.lib.config.loaders.workstation.lib.CustomVaultRecyclerConfig;
import xyz.iwolfking.vhapi.api.lib.loaders.VaultConfigDataLoader;

import java.util.HashMap;

public class VaultRecyclerConfigLoader extends VaultConfigDataLoader<CustomVaultRecyclerConfig> {
    public VaultRecyclerConfigLoader(String namespace) {
        super(new CustomVaultRecyclerConfig(), "vault_recycler", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(CustomVaultRecyclerConfig config : this.CUSTOM_CONFIGS.values()) {
            CustomRecyclerOutputs.CUSTOM_OUTPUTS.putAll(config.getOutputs());
        }

    }
}
