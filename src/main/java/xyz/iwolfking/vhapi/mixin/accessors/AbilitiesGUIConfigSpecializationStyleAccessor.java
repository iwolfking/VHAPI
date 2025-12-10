package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.AbilitiesGUIConfig;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = AbilitiesGUIConfig.SpecializationStyle.class, remap = false)
public interface AbilitiesGUIConfigSpecializationStyleAccessor {
}
