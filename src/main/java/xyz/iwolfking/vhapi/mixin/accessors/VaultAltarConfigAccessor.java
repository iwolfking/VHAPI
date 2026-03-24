package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.VaultAltarConfig;
import iskallia.vault.config.VaultCrystalConfig;
import iskallia.vault.config.entry.LevelEntryList;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Map;

@Mixin(value = VaultAltarConfig.class, remap = false)
public interface VaultAltarConfigAccessor {
    @Accessor("INTERFACES")
    void setInterfaces(List<VaultAltarConfig.Interface> interfaces);
}
