package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.ChampionsConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ChampionsConfig.DifficultyRule.class)
public interface ChampionsConfigDifficultyRuleAccessor {

    @Accessor("aura")
    boolean getAura();

    @Accessor("aura")
    void setAura(boolean aura);

    @Accessor("onHit")
    boolean getOnHit();

    @Accessor("onHit")
    void setOnHit(boolean onHit);
}