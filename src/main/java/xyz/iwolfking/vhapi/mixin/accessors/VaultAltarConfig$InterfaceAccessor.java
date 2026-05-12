package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.VaultAltarConfig;
import iskallia.vault.core.world.data.item.ItemPredicate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = VaultAltarConfig.Interface.class, remap = false)
public interface VaultAltarConfig$InterfaceAccessor {
    @Accessor("input")
    ItemPredicate getInput();

}
