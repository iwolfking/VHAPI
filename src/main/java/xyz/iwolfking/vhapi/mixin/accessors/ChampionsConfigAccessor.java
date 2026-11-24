package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.ChampionsConfig;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ChampionsConfig.class)
public interface ChampionsConfigAccessor {

    @Accessor("minimumVaultLevel")
    @Nullable
    Integer getMinimumVaultLevel();

    @Accessor("minimumVaultLevel")
    void setMinimumVaultLevel(@Nullable Integer minimumVaultLevel);

    @Accessor("rules")
    @Nullable
    ChampionsConfig.Rules getRules();

    @Accessor("rules")
    void setRules(@Nullable ChampionsConfig.Rules rules);

    @Accessor("auras")
    @Nullable
    Map<String, ChampionsConfig.AuraSettings> getAuras();

    @Accessor("auras")
    void setAuras(@Nullable Map<String, ChampionsConfig.AuraSettings> auras);

    @Accessor("onHits")
    @Nullable
    Map<String, ChampionsConfig.OnHitSettings> getOnHits();

    @Accessor("onHits")
    void setOnHits(@Nullable Map<String, ChampionsConfig.OnHitSettings> onHits);
}