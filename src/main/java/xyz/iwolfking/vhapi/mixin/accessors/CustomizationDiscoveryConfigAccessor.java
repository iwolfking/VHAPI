package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.customisation.CustomisationDiscovery;
import iskallia.vault.config.customisation.CustomisationDiscoveryConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = CustomisationDiscoveryConfig.class, remap = false)
public interface CustomizationDiscoveryConfigAccessor {
    @Accessor("prefixes")
    Map<String, CustomisationDiscovery> getPrefixes();

    @Accessor("suffixes")
    Map<String, CustomisationDiscovery> getSuffixes();

    @Accessor("emblems")
    Map<String, CustomisationDiscovery> getEmblems();

    @Accessor("trails")
    Map<String, CustomisationDiscovery> getTrails();
}
