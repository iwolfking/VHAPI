package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.workstation.lib;

import com.google.gson.annotations.Expose;
import iskallia.vault.config.Config;
import iskallia.vault.config.EntityGroupsConfig;
import iskallia.vault.config.VaultLevelsConfig;
import iskallia.vault.config.VaultRecyclerConfig;
import iskallia.vault.core.data.adapter.Adapters;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class CustomVaultRecyclerConfig extends Config {

    @Expose
    public Map<ResourceLocation, GsonRecyclerOutput> OUTPUTS;
    @Override
    public String getName() {
        return "custom_vault_recycler";
    }

    @Override
    protected void reset() {

    }

    public Map<ResourceLocation, VaultRecyclerConfig.RecyclerOutput> getOutputs() {
        Map<ResourceLocation, VaultRecyclerConfig.RecyclerOutput> convertedOuputs = new HashMap<>();
        for(ResourceLocation key : OUTPUTS.keySet()) {
            convertedOuputs.put(key, OUTPUTS.get(key).getOutput());
        }
        return convertedOuputs;
    }
}