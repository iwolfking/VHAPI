package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.AbilitiesConfig;
import iskallia.vault.config.AbilitiesDescriptionsConfig;
import iskallia.vault.skill.tree.AbilityTree;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.TreeMap;

@Mixin(value = AbilitiesConfig.class, remap = false)
public interface AbilitiesConfigAccessor {
    @Accessor("tree")
    void setTree(AbilityTree tree);
}
