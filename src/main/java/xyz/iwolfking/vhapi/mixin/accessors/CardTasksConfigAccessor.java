package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.card.CardTasksConfig;
import iskallia.vault.core.util.WeightedList;
import iskallia.vault.task.Task;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = CardTasksConfig.class, remap = false)
public interface CardTasksConfigAccessor {
    @Accessor("values")
    public Map<String, Task> getValues();
    @Accessor("pools")
    public Map<String, WeightedList<String>> getPools();
}
