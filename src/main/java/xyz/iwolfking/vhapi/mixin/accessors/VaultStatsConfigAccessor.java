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

    @Accessor("blocksMined")
    public Map<ResourceLocation, Float> getBlocksMinedMap();

    @Accessor("treasureRoomsOpened")
    public void setTreasureRoomsOpened(float experience);

    @Accessor("mobsKilled")
    public Map<ResourceLocation, Float> getMobsKilledMap();

    @Accessor("completion")
    public Map<String, Map<Completion, Float>> getCompletionMap();

    @Accessor("percentOfExperienceDealtAsDurabilityDamage")
    public void setPercentOfExperienceDealtAsDurabilityDamage(float experience);

    @Accessor("freeExperienceNotDealtAsDurabilityDamage")
    public void setFreeExperienceNotDealtAsDurabilityDamage(int experience);



}
