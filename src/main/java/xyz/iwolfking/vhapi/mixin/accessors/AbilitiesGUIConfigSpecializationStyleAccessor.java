package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.AbilitiesGUIConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = AbilitiesGUIConfig.SpecializationStyle.class, remap = false)
public interface AbilitiesGUIConfigSpecializationStyleAccessor {
    @Accessor("type")
    void setType(AbilitiesGUIConfig.Type type);
}
