package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.VaultModifiersConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = VaultModifiersConfig.ModifierTypeGroups.class, remap = false)
public interface ModifierTypeGroupsAccessor {
    @Invoker("<init>")
    static VaultModifiersConfig.ModifierTypeGroups create() {
        return null;
    }
}
