package xyz.iwolfking.vhapi.api.lib.config.loaders.vault.portal;


import iskallia.vault.config.VaultPortalConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.lib.loaders.VaultConfigDataLoader;

import java.util.*;

public class VaultPortalBlockConfigLoader extends VaultConfigDataLoader<VaultPortalConfig> {
    public VaultPortalBlockConfigLoader(String namespace) {
        super(new VaultPortalConfig(), "vault/portal", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        List<String> newPortalBlocks = new ArrayList<>();
        for(VaultPortalConfig config : this.CUSTOM_CONFIGS.values()) {
            newPortalBlocks.addAll(Arrays.stream(config.VALID_BLOCKS).toList());
        }

        if(!newPortalBlocks.isEmpty()) {
            ArrayList<String> portalBlocks = (ArrayList<String>) new ArrayList<>(Arrays.asList(ModConfigs.VAULT_PORTAL.VALID_BLOCKS));
            newPortalBlocks.addAll(portalBlocks);
            ModConfigs.VAULT_PORTAL.VALID_BLOCKS = newPortalBlocks.toArray(String[]::new);
        }

    }
}
