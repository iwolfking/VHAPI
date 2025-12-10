package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.AbilitiesGroups;
import iskallia.vault.config.ChallengeCurseConfig;
import iskallia.vault.core.util.WeightedList;
import iskallia.vault.core.vault.challenge.curse.ChallengeCurse;
import iskallia.vault.skill.ability.AbilityType;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Map;

@Mixin(value = ChallengeCurseConfig.class, remap = false)
public interface ChallengeCurseConfigAccessor {
    @Accessor("curseWeights")
    Map<String, WeightedList<ResourceLocation>> getCurseWeights();

    @Accessor("curseWeights")
    void setCurseWeights(Map<String, WeightedList<ResourceLocation>> curseWeights);

    @Accessor("curses")
    Map<ResourceLocation, ChallengeCurse<?>> getAllCurses();

    @Accessor("curses")
    void setCurses(Map<ResourceLocation, ChallengeCurse<?>> curses);
}
