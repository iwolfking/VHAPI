package xyz.iwolfking.vaultcrackerlib.mixin.accessors;

import iskallia.vault.config.VaultModifiersConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = VaultModifiersConfig.class, remap = false)
public interface VaultModifiersConfigAccessor {
    @Accessor("modifierTypeGroups")
    public VaultModifiersConfig.ModifierTypeGroups getModifierTypeGroups();

    @Accessor("modifierTypeGroups")
    public void setModifierTypeGroups(VaultModifiersConfig.ModifierTypeGroups modifierTypeGroups);
}
