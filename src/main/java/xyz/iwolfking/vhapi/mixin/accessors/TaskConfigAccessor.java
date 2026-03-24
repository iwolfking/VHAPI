package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.bounty.task.properties.TaskProperties;
import iskallia.vault.config.bounty.task.TaskConfig;
import iskallia.vault.config.bounty.task.entry.TaskEntry;
import iskallia.vault.config.entry.LevelEntryMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = TaskConfig.class, remap = false)
public interface TaskConfigAccessor<E extends TaskEntry<?>, T extends TaskProperties> {
    @Accessor("LEVELS")
    void setLevels(LevelEntryMap<E> map);

    @Accessor("LEVELS")
    LevelEntryMap<E> getLevels();
}
