package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.customisation.CustomisationDiscovery;
import iskallia.vault.config.customisation.CustomisationDiscoveryConfig;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CustomisationDiscoveryConfig.class)
public interface CustomisationDiscoveryConfigAccessor {

    @Accessor("prefixes")
    @Nullable
    Map<String, CustomisationDiscovery> getPrefixes();

    @Accessor("prefixes")
    void setPrefixes(@Nullable Map<String, CustomisationDiscovery> prefixes);

    @Accessor("suffixes")
    @Nullable
    Map<String, CustomisationDiscovery> getSuffixes();

    @Accessor("suffixes")
    void setSuffixes(@Nullable Map<String, CustomisationDiscovery> suffixes);

    @Accessor("emblems")
    @Nullable
    Map<String, CustomisationDiscovery> getEmblems();

    @Accessor("emblems")
    void setEmblems(@Nullable Map<String, CustomisationDiscovery> emblems);

    @Accessor("trails")
    @Nullable
    Map<String, CustomisationDiscovery> getTrails();

    @Accessor("trails")
    void setTrails(@Nullable Map<String, CustomisationDiscovery> trails);

    @Accessor("gearModels")
    @Nullable
    Map<ResourceLocation, CustomisationDiscovery> getGearModels();

    @Accessor("gearModels")
    void setGearModels(@Nullable Map<ResourceLocation, CustomisationDiscovery> gearModels);
}