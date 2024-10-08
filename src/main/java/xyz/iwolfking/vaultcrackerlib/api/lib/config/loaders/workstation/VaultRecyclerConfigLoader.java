package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.workstation;

import iskallia.vault.config.VaultRecyclerConfig;
import iskallia.vault.config.tool.ToolPulverizingConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vaultcrackerlib.api.data.CustomRecyclerOutputs;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.workstation.lib.CustomVaultRecyclerConfig;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;

import java.util.HashMap;
import java.util.Map;

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
