package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.ChallengeCrystalConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = ChallengeCrystalConfig.class, remap = false)
public interface ChallengeCrystalConfigAccessor {
    @Accessor("challenges")
    Map<String, ChallengeCrystalConfig.Entry> getChallengesMap();

    @Accessor("challenges")
    void setChallengesMap(Map<String, ChallengeCrystalConfig.Entry> challenges);
}
