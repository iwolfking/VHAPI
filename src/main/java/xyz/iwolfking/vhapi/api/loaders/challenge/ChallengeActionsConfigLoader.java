package xyz.iwolfking.vhapi.api.loaders.challenge;

import iskallia.vault.config.ChallengeActionsConfig;
import iskallia.vault.core.vault.challenge.action.ChallengeAction;
import iskallia.vault.init.ModConfigs;
import java.util.HashMap;
import java.util.Map;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

public class ChallengeActionsConfigLoader extends VaultConfigProcessor<ChallengeActionsConfig> {
    public ChallengeActionsConfigLoader() {
        super(new ChallengeActionsConfig(), "challenge/actions");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        ChallengeActionsConfig baseConfig = ModConfigs.CHALLENGE_ACTIONS;
        if (baseConfig == null) {
            super.afterConfigsLoad(event);
            return;
        }

        if (baseConfig.values == null) {
            baseConfig.values = new HashMap<>();
        }

        for (ChallengeActionsConfig customConfig : this.CUSTOM_CONFIGS.values()) {
            if (customConfig.values == null || customConfig.values.isEmpty()) {
                continue;
            }
            for (Map.Entry<String, ChallengeAction<?>> entry : customConfig.values.entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null) {
                    baseConfig.values.put(entry.getKey(), entry.getValue());
                }
            }
        }

        super.afterConfigsLoad(event);
    }
}