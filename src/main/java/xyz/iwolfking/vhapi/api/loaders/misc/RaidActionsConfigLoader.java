package xyz.iwolfking.vhapi.api.loaders.misc;

import iskallia.vault.config.RaidActionsConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.RaidActionsConfigAccessor;

public class RaidActionsConfigLoader extends VaultConfigProcessor<RaidActionsConfig> {
    public RaidActionsConfigLoader() {
        super(new RaidActionsConfig(), "raid_actions");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ResourceLocation key : this.CUSTOM_CONFIGS.keySet()) {
            RaidActionsConfig config = CUSTOM_CONFIGS.get(key);
            ((RaidActionsConfigAccessor)config).getValues().forEach((s, challengeAction) -> ((RaidActionsConfigAccessor) ModConfigs.RAID_ACTIONS).getValues().put(s, challengeAction));
        }
        super.afterConfigsLoad(event);
    }
}
