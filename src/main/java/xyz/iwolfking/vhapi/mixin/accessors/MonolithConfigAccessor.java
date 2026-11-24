package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.MonolithConfig;
import iskallia.vault.config.entry.LevelEntryList;
import iskallia.vault.core.vault.objective.brazier.BrazierHeatTier;
import iskallia.vault.world.VaultDifficulty;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MonolithConfig.class)
public interface MonolithConfigAccessor {

    @Accessor("tiers")
    Map<BrazierHeatTier, Object> getTiers();

    @Accessor("tiers")
    void setTiers(Map<BrazierHeatTier, Object> tiers);

    @Accessor("rollNearDistance")
    double getRollNearDistance();

    @Accessor("rollNearDistance")
    void setRollNearDistance(double rollNearDistance);

    @Accessor("ringRadius")
    double getRingRadius();

    @Accessor("ringRadius")
    void setRingRadius(double ringRadius);

    @Accessor("ringHeight")
    double getRingHeight();

    @Accessor("ringHeight")
    void setRingHeight(double ringHeight);

    @Accessor("contestedSpeed")
    double getContestedSpeed();

    @Accessor("contestedSpeed")
    void setContestedSpeed(double contestedSpeed);

    @Accessor("coldDrainSpeed")
    double getColdDrainSpeed();

    @Accessor("coldDrainSpeed")
    void setColdDrainSpeed(double coldDrainSpeed);

    @Accessor("secondWaveAt")
    double getSecondWaveAt();

    @Accessor("secondWaveAt")
    void setSecondWaveAt(double secondWaveAt);

    @Accessor("mobHealthPerHeat")
    double getMobHealthPerHeat();

    @Accessor("mobHealthPerHeat")
    void setMobHealthPerHeat(double mobHealthPerHeat);

    @Accessor("mobDamagePerHeat")
    double getMobDamagePerHeat();

    @Accessor("mobDamagePerHeat")
    void setMobDamagePerHeat(double mobDamagePerHeat);

    @Accessor("waveSpawnMinDistance")
    double getWaveSpawnMinDistance();

    @Accessor("waveSpawnMinDistance")
    void setWaveSpawnMinDistance(double waveSpawnMinDistance);

    @Accessor("waveSpawnMaxDistance")
    double getWaveSpawnMaxDistance();

    @Accessor("waveSpawnMaxDistance")
    void setWaveSpawnMaxDistance(double waveSpawnMaxDistance);

    @Accessor("waveMobGroups")
    Map<VaultDifficulty, Map<String, Integer>> getWaveMobGroups();

    @Accessor("waveMobGroups")
    void setWaveMobGroups(Map<VaultDifficulty, Map<String, Integer>> waveMobGroups);

    @Accessor("blessingCaps")
    Map<ResourceLocation, Integer> getBlessingCaps();

    @Accessor("blessingCaps")
    void setBlessingCaps(Map<ResourceLocation, Integer> blessingCaps);

    @Accessor("levels")
    LevelEntryList<MonolithConfig.LevelEntry> getLevels();

    @Accessor("levels")
    void setLevels(LevelEntryList<MonolithConfig.LevelEntry> levels);
}