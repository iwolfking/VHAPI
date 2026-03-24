package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.AbilitiesGroups;
import iskallia.vault.skill.ability.AbilityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Map;

@Mixin(value = AbilitiesGroups.class, remap = false)
public interface AbilitiesGroupsAccessor {
    @Accessor("types")
    Map<AbilityType, List<String>> getTypes();

    @Accessor("types")
    void setTypes(Map<AbilityType, List<String>> types);
}
