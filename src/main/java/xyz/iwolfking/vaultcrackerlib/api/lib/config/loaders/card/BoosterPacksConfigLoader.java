package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.card;

import iskallia.vault.config.card.BoosterPackConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;
import java.util.HashMap;


public class BoosterPacksConfigLoader extends VaultConfigDataLoader<BoosterPackConfig> {
    public BoosterPacksConfigLoader(String namespace) {
        super(new BoosterPackConfig(), "card/booster_packs", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(BoosterPackConfig config : this.CUSTOM_CONFIGS.values()) {
            ModConfigs.BOOSTER_PACK.getValues().putAll(config.getValues());
        }

    }
}
