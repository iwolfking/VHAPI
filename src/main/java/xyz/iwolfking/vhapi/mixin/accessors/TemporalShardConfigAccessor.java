package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.TemporalShardConfig;
import iskallia.vault.config.entry.LevelEntryList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = TemporalShardConfig.class, remap = false)
public interface TemporalShardConfigAccessor {
    @Accessor
    LevelEntryList<TemporalShardConfig.Level> getModifiers();

    @Accessor
    void setModifiers(LevelEntryList<TemporalShardConfig.Level> levels);
}
