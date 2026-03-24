package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.tool.ToolPulverizingConfig;
import iskallia.vault.core.world.data.entity.PartialEntity;
import iskallia.vault.core.world.loot.LootTable;
import iskallia.vault.core.world.roll.IntRoll;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Map;

@Mixin(value = ToolPulverizingConfig.class,remap = false)
public interface ToolPulverizingConfigAccessor {
    @Accessor("loot")
    void setLoot(Map<ResourceLocation, List<LootTable.Entry>> loot);
}
