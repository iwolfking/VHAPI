package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.CompanionRelicsConfig;
import iskallia.vault.config.entry.LevelEntryList;
import iskallia.vault.core.util.WeightedList;
import iskallia.vault.util.VaultRarity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = CompanionRelicsConfig.Pool.class, remap = false)
public interface CompanionRelicPoolAccessor {
    @Accessor("pool")
    WeightedList<CompanionRelicsConfig.Entry> getPool();
}
