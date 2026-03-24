package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.AbilitiesConfig;
import iskallia.vault.config.card.BoosterPackConfig;
import iskallia.vault.skill.tree.AbilityTree;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = BoosterPackConfig.class, remap = false)
public interface BoosterPackConfigAccessor {
    @Accessor("values")
    void setValues(Map<String, BoosterPackConfig.BoosterPackEntry> values);
}
