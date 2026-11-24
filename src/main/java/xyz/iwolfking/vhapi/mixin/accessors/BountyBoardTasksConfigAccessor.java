package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.bounty.BountyBoardTasksConfig;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BountyBoardTasksConfig.class)
public interface BountyBoardTasksConfigAccessor {

    @Accessor("tasks")
    List<BountyBoardTasksConfig.Entry> vhapi$getTasks();

    @Accessor("tasks")
    void vhapi$setTasks(List<BountyBoardTasksConfig.Entry> tasks);
}