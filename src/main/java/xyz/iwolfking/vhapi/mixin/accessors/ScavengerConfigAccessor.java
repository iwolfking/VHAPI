package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.ScavengerConfig;
import iskallia.vault.core.vault.objective.scavenger.ScavengeTask;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(value = ScavengerConfig.class, remap = false)
public interface ScavengerConfigAccessor {


    @Accessor("tasks")
    public void setTasks(List<ScavengeTask> tasks);

    @Accessor("tasks")
    public List<ScavengeTask> getTasks();





}
