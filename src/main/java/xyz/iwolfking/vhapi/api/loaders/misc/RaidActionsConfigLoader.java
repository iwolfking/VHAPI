package xyz.iwolfking.vhapi.api.loaders.misc;

import iskallia.vault.block.entity.challenge.raid.action.PoolChallengeAction;
import iskallia.vault.config.ChampionsConfig;
import iskallia.vault.config.RaidActionsConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.PoolChallengeActionAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.RaidActionsConfigAccessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RaidActionsConfigLoader extends VaultConfigProcessor<RaidActionsConfig> {
    public RaidActionsConfigLoader() {
        super(new RaidActionsConfig(), "raid_actions");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ResourceLocation key : this.CUSTOM_CONFIGS.keySet()) {
            RaidActionsConfig config = CUSTOM_CONFIGS.get(key);
            ((RaidActionsConfigAccessor)config).getValues().forEach((s, challengeAction) -> {
                if(((RaidActionsConfigAccessor)ModConfigs.RAID_ACTIONS).getValues().containsKey(s)) {
                    if(((RaidActionsConfigAccessor)ModConfigs.RAID_ACTIONS).getValues().get(s) instanceof PoolChallengeAction poolChallengeAction) {
                        if(challengeAction instanceof PoolChallengeAction newPoolChallengeAction) {
                            ((PoolChallengeActionAccessor)newPoolChallengeAction).getPool().forEach((challengeAction1, aDouble) -> {
                                ((PoolChallengeActionAccessor)poolChallengeAction).getPool().add(challengeAction1, aDouble);
                            });
                        }
                    }
                    else {
                        ((RaidActionsConfigAccessor) ModConfigs.RAID_ACTIONS).getValues().put(s, challengeAction);
                    }
                }
                else {
                    ((RaidActionsConfigAccessor) ModConfigs.RAID_ACTIONS).getValues().put(s, challengeAction);
                }
            });
        }
        super.afterConfigsLoad(event);
    }
}
