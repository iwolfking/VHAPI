package xyz.iwolfking.vaultcrackerlib.mixin.accessors;

import iskallia.vault.config.Config;
import iskallia.vault.config.ElixirConfig;
import iskallia.vault.config.entry.LevelEntryList;
import iskallia.vault.core.world.data.entity.EntityPredicate;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.io.File;
import java.util.Map;

@Mixin(value = ElixirConfig.class, remap = false)
public interface ElixirConfigAccessor {
    @Accessor("elixirToSize")
    public void setElixirToSize(Map<Integer, Integer> elixirToSize);

    @Accessor("elixirToSize")
    public Map<Integer, Integer> getElixirToSize();

    @Accessor("mobGroups")
    public void setMobGroups(Map<ResourceLocation, EntityPredicate> mobGroups);

    @Accessor("mobGroups")
    public Map<ResourceLocation, EntityPredicate> getMobGroups();

    @Accessor("entries")
    public void setEntries(LevelEntryList<ElixirConfig.Entry> entries);

    @Accessor("entries")
    public LevelEntryList<ElixirConfig.Entry> getEntries();


}
