package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.AbilitiesDescriptionsConfig;
import iskallia.vault.config.VaultEntitiesConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.TreeMap;

@Mixin(value = VaultEntitiesConfig.class, remap = false)
public interface VaultEntitiesConfigAccessor {
    @Accessor("deathEffects")
    @Mutable
    void setDeathEffects(List<VaultEntitiesConfig.DeathEffect> deathEffects);

    @Accessor("deathEffects")
    List<VaultEntitiesConfig.DeathEffect> getDeathEffects();

    @Accessor("throwEffects")
    @Mutable
    void setThrowEffects(List<VaultEntitiesConfig.ThrowEffect> deathEffects);

    @Accessor("throwEffects")
    List<VaultEntitiesConfig.ThrowEffect> getThrowEffects();
}
