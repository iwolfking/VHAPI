package xyz.iwolfking.vhapi.api.loaders.misc;

import iskallia.vault.config.ChallengeActionsConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.RaidActionsConfigAccessor;

public class RaidActionsConfigLoader extends VaultConfigProcessor<ChallengeActionsConfig> {
    public RaidActionsConfigLoader() {
        super(new ChallengeActionsConfig(), "challenge_actions");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ResourceLocation key : this.CUSTOM_CONFIGS.keySet()) {
            ChallengeActionsConfig config = CUSTOM_CONFIGS.get(key);
            ((RaidActionsConfigAccessor)config).getValues().forEach((s, challengeAction) -> ((RaidActionsConfigAccessor) ModConfigs.CHALLENGE_ACTIONS).getValues().put(s, challengeAction));
        }
        super.afterConfigsLoad(event);
    }
}
