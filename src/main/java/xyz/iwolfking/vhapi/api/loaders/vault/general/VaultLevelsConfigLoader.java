package xyz.iwolfking.vhapi.api.loaders.vault.general;

import iskallia.vault.config.VaultLevelsConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.VaultLevelsConfigAccessor;

public class VaultLevelsConfigLoader extends VaultConfigProcessor<VaultLevelsConfig> {

    public VaultLevelsConfigLoader() {
        super(new VaultLevelsConfig(), "vault/levels");
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
