package xyz.iwolfking.vhapi.api.loaders.gear;


import iskallia.vault.config.gear.VaultEtchingConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

public class EtchingConfigLoader extends VaultConfigProcessor<VaultEtchingConfig> {

    public EtchingConfigLoader() {
        super(new VaultEtchingConfig(), "gear/etching");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        this.CUSTOM_CONFIGS.forEach((resourceLocation, vaultEtchingConfig) -> {
            ModConfigs.ETCHINGS.ETCHINGS.putAll(vaultEtchingConfig.ETCHINGS);
        });
        super.afterConfigsLoad(event);
    }
}
