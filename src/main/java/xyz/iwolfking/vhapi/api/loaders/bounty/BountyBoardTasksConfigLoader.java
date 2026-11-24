package xyz.iwolfking.vhapi.api.loaders.bounty;

import iskallia.vault.config.bounty.BountyBoardTasksConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.BountyBoardTasksConfigAccessor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BountyBoardTasksConfigLoader extends VaultConfigProcessor<BountyBoardTasksConfig> {
    public BountyBoardTasksConfigLoader() {
        super(new BountyBoardTasksConfig(), "bounty/tasks");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        List<BountyBoardTasksConfig.Entry> baseTasks =
                ((BountyBoardTasksConfigAccessor) ModConfigs.BOUNTY_BOARD_TASKS).vhapi$getTasks();

        for (BountyBoardTasksConfig config : this.CUSTOM_CONFIGS.values()) {
            List<BountyBoardTasksConfig.Entry> customTasks =
                    ((BountyBoardTasksConfigAccessor) config).vhapi$getTasks();
            Set<BountyBoardTasksConfig.Entry> entriesToRemove = new HashSet<>();

            for (BountyBoardTasksConfig.Entry entry : customTasks) {
                for (BountyBoardTasksConfig.Entry existingEntry : baseTasks) {
                    if (entry.getId() != null && entry.getId().equals(existingEntry.getId())) {
                        entriesToRemove.add(existingEntry);
                    }
                }
            }

            baseTasks.removeAll(entriesToRemove);
            baseTasks.addAll(customTasks);
        }

        super.afterConfigsLoad(event);
    }
}