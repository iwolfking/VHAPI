package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.PrestigePowersConfig;
import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PrestigePowersConfig.Ladder.class)
public interface PrestigePowersConfigLadderAccessor {

    @Accessor("tierPrices")
    List<Integer> getTierPrices();

    @Accessor("tierPrices")
    void setTierPrices(List<Integer> tierPrices);

    @Accessor("stepPercentPerTierOwned")
    Integer getStepPercentPerTierOwned();

    @Accessor("stepPercentPerTierOwned")
    void setStepPercentPerTierOwned(Integer stepPercentPerTierOwned);

    @Accessor("unlockLevel")
    Integer getUnlockLevel();

    @Accessor("unlockLevel")
    void setUnlockLevel(Integer unlockLevel);

    @Accessor("requireHerald")
    Boolean getRequireHerald();

    @Accessor("requireHerald")
    void setRequireHerald(Boolean requireHerald);
}