package xyz.iwolfking.vaultcrackerlib.mixin.accessors;

import iskallia.vault.config.ElixirConfig;
import iskallia.vault.config.ScavengerConfig;
import iskallia.vault.config.entry.LevelEntryList;
import iskallia.vault.core.vault.objective.scavenger.ScavengeTask;
import iskallia.vault.core.world.data.entity.EntityPredicate;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Map;

@Mixin(value = ScavengerConfig.class, remap = false)
public interface ScavengerConfigAccessor {


    @Accessor("tasks")
    public void setTasks(List<ScavengeTask> tasks);

    @Accessor("tasks")
    public List<ScavengeTask> getTasks();





}
