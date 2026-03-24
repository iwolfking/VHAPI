package xyz.iwolfking.vhapi.api.loaders.challenge;

import iskallia.vault.config.ChallengeCurseConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.ChallengeCurseConfigAccessor;


public class ChallengeCurseConfigLoader extends VaultConfigProcessor<ChallengeCurseConfig> {

    public ChallengeCurseConfigLoader() {
        super(new ChallengeCurseConfig(), "challenge/curse");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        CUSTOM_CONFIGS.forEach(((resourceLocation, challengeCurseConfig) -> {
            if(resourceLocation.getPath().contains("overwrite")) {
                ModConfigs.CHALLENGE_CURSES = challengeCurseConfig;
            }
            else if(resourceLocation.getPath().contains("replace")) {
                ModConfigs.CHALLENGE_CURSES.getCurses().putAll(challengeCurseConfig.getCurses());
                ((ChallengeCurseConfigAccessor)ModConfigs.CHALLENGE_CURSES).getCurseWeights().putAll(((ChallengeCurseConfigAccessor)challengeCurseConfig).getCurseWeights());
            }
            else {
                ModConfigs.CHALLENGE_CURSES.getCurses().putAll(challengeCurseConfig.getCurses());
                ((ChallengeCurseConfigAccessor)challengeCurseConfig).getCurseWeights().forEach((s, resourceLocationWeightedList) -> {
                    if(((ChallengeCurseConfigAccessor)ModConfigs.CHALLENGE_CURSES).getCurseWeights().containsKey(s)) {
                        resourceLocationWeightedList.forEach(((resourceLocation1, aDouble) -> {
                            ((ChallengeCurseConfigAccessor)ModConfigs.CHALLENGE_CURSES).getCurseWeights().get(s).add(resourceLocation1, aDouble);
                        }));
                    }
                    else {
                        ((ChallengeCurseConfigAccessor)ModConfigs.CHALLENGE_CURSES).getCurseWeights().put(s, resourceLocationWeightedList);
                    }
                });
            }
        }));
        super.afterConfigsLoad(event);
    }
}
