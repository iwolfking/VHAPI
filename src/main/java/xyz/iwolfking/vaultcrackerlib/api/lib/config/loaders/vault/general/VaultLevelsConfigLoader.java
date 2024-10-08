package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.vault.general;

import iskallia.vault.config.VaultLevelsConfig;
import iskallia.vault.config.tool.ToolPulverizingConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.VaultLevelsConfigAccessor;

import java.util.HashMap;
import java.util.Map;

public class VaultLevelsConfigLoader extends VaultConfigDataLoader<VaultLevelsConfig> {

    public VaultLevelsConfigLoader(String namespace) {
        super(new VaultLevelsConfig(), "vault/levels", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for (ResourceLocation key : this.CUSTOM_CONFIGS.keySet()) {
            if(key.getPath().contains("overwrite")) {
                ModConfigs.LEVELS_META = CUSTOM_CONFIGS.get(key);
            }
            else {
                if(((VaultLevelsConfigAccessor)CUSTOM_CONFIGS.get(key)).getLevelMetas() != null) {
                    ((VaultLevelsConfigAccessor)ModConfigs.LEVELS_META).getLevelMetas().addAll(((VaultLevelsConfigAccessor) CUSTOM_CONFIGS.get(key)).getLevelMetas());
                }
                if(CUSTOM_CONFIGS.get(key).getMaxLevel() != 100) {
                    ((VaultLevelsConfigAccessor)ModConfigs.LEVELS_META).setMaxLevel(CUSTOM_CONFIGS.get(key).getMaxLevel());
                }
            }
        }

    }
}
