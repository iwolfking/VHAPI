package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.VaultMetaChestConfig;
import iskallia.vault.util.VaultRarity;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = VaultMetaChestConfig.class, remap = false)
public interface VaultMetaChestConfigAccessor {
    @Accessor("catalystChances")
    public Map<Block, Map<VaultRarity, Double>> getCatalystChances();

    @Accessor("catalystMinLevel")
    public void setCatalystMinLevel(int level);
}
