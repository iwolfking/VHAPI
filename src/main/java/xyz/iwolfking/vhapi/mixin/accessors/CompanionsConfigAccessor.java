package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.CompanionsConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = CompanionsConfig.class, remap = false)
public interface CompanionsConfigAccessor {
    @Accessor("PET_WEIGHTS")
    Map<String, Double> getPetWeights();
}
