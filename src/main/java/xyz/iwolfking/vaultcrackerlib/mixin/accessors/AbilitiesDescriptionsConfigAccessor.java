package xyz.iwolfking.vaultcrackerlib.mixin.accessors;

import iskallia.vault.config.AbilitiesDescriptionsConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.TreeMap;

@Mixin(value = AbilitiesDescriptionsConfig.class, remap = false)
public interface AbilitiesDescriptionsConfigAccessor {
    @Accessor("data")
    public TreeMap<String, AbilitiesDescriptionsConfig.DescriptionData> getData();
}
