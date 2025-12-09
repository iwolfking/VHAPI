package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.AbilitiesConfig;
import iskallia.vault.config.gear.VaultGearTagConfig;
import iskallia.vault.skill.tree.AbilityTree;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = VaultGearTagConfig.class, remap = false)
public interface VaultGearTagConfigAccessor {
    @Accessor("tags")
    Map<String, VaultGearTagConfig.ModTagGroup> getAllTags();
}
