package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.ChampionsConfig;
import java.util.List;
import java.util.Map;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ChampionsConfig.Rules.class)
public interface ChampionsConfigRulesAccessor {

    @Accessor("checkIntervalTicks")
    int getCheckIntervalTicks();

    @Accessor("checkIntervalTicks")
    void setCheckIntervalTicks(int checkIntervalTicks);

    @Accessor("heightBlocks")
    float getHeightBlocks();

    @Accessor("heightBlocks")
    void setHeightBlocks(float heightBlocks);

    @Accessor("targets")
    List<String> getTargets();

    @Accessor("targets")
    void setTargets(List<String> targets);

    @Accessor("difficulty")
    Map<String, ChampionsConfig.DifficultyRule> getDifficulty();

    @Accessor("difficulty")
    void setDifficulty(Map<String, ChampionsConfig.DifficultyRule> difficulty);
}