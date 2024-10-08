package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.vault.chest;

import iskallia.vault.config.VaultMetaChestConfig;
import iskallia.vault.config.tool.ToolPulverizingConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.VaultMetaChestConfigAccessor;

import java.util.HashMap;
import java.util.Map;

public class VaultChestMetaConfigLoader extends VaultConfigDataLoader<VaultMetaChestConfig> {
    public VaultChestMetaConfigLoader(String namespace) {
        super(new VaultMetaChestConfig(), "vault/chest_meta", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(VaultMetaChestConfig config : this.CUSTOM_CONFIGS.values()) {
            ((VaultMetaChestConfigAccessor)ModConfigs.VAULT_CHEST_META).getCatalystChances().putAll(((VaultMetaChestConfigAccessor)config).getCatalystChances());
            if(config.getCatalystMinLevel() != 20) {
                ((VaultMetaChestConfigAccessor) ModConfigs.VAULT_CHEST_META).setCatalystMinLevel(config.getCatalystMinLevel());
            }
        }

    }
}
