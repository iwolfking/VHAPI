package xyz.iwolfking.vaultcrackerlib.mixin.accessors;

import com.google.gson.annotations.Expose;
import iskallia.vault.config.card.CardTasksConfig;
import iskallia.vault.core.util.WeightedList;
import iskallia.vault.task.Task;
import org.checkerframework.checker.units.qual.A;
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
