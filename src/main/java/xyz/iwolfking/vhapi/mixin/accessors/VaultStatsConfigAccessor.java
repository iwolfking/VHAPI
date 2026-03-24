package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.VaultStatsConfig;
import iskallia.vault.core.vault.player.Completion;
import iskallia.vault.core.vault.stat.VaultChestType;
import iskallia.vault.util.VaultRarity;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = VaultStatsConfig.class, remap = false)
public interface VaultStatsConfigAccessor {
    @Accessor("chests")
    public Map<VaultChestType, Map<VaultRarity, Float>> getChestsMap();

    @Accessor("chests")
    public void setChestsMap(Map<VaultChestType, Map<VaultRarity, Float>> map);

    @Accessor("barrels")
    public Map<VaultChestType, Map<VaultRarity, Float>> getBarrelsMap();

    @Accessor("barrels")
    public void setBarrelsMap(Map<VaultChestType, Map<VaultRarity, Float>> map);

    @Accessor("blocksMined")
    public Map<ResourceLocation, Float> getBlocksMinedMap();

    @Accessor("blocksMined")
    public void setBlocksMinedMap(Map<ResourceLocation, Float> map);

    @Accessor("treasureRoomsOpened")
    public void setTreasureRoomsOpened(float experience);

    @Accessor("mobsKilled")
    public Map<ResourceLocation, Float> getMobsKilledMap();

    @Accessor("mobsKilled")
    public void setMobsKilledMap(Map<ResourceLocation, Float> map);

    @Accessor("completion")
    public Map<String, Map<Completion, Float>> getCompletionMap();

    @Accessor("completion")
    public void setCompletionMap(Map<String, Map<Completion, Float>> map);

    @Accessor("percentOfExperienceDealtAsDurabilityDamage")
    public void setPercentOfExperienceDealtAsDurabilityDamage(float experience);

    @Accessor("freeExperienceNotDealtAsDurabilityDamage")
    public void setFreeExperienceNotDealtAsDurabilityDamage(int experience);



}
