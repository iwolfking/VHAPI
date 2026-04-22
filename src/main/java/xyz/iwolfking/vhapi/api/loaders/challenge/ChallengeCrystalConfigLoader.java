package xyz.iwolfking.vhapi.api.loaders.challenge;

import iskallia.vault.config.ChallengeCrystalConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

public class ChallengeCrystalConfigLoader extends VaultConfigProcessor<ChallengeCrystalConfig> {
    public ChallengeCrystalConfigLoader() {
        super(new ChallengeCrystalConfig(), "challenge_crystal");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        this.CUSTOM_CONFIGS.forEach((resourceLocation, challengeCrystalConfig) -> {
            ModConfigs.CHALLENGE_CRYSTALS.getChallenges().putAll(challengeCrystalConfig.getChallenges());
        });
        super.afterConfigsLoad(event);
    }
}
