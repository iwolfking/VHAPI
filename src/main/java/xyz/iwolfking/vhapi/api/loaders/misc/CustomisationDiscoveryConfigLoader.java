package xyz.iwolfking.vhapi.api.loaders.misc;

import iskallia.vault.config.customisation.CustomisationDiscovery;
import iskallia.vault.config.customisation.CustomisationDiscoveryConfig;
import iskallia.vault.init.ModConfigs;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.CustomisationDiscoveryConfigAccessor;

public class CustomisationDiscoveryConfigLoader extends VaultConfigProcessor<CustomisationDiscoveryConfig> {
    public CustomisationDiscoveryConfigLoader() {
        super(new CustomisationDiscoveryConfig(), "customisation_discovery");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        CustomisationDiscoveryConfigAccessor base =
                (CustomisationDiscoveryConfigAccessor) ModConfigs.CUSTOMISATION_DISCOVERY;

        for (CustomisationDiscoveryConfig config : this.CUSTOM_CONFIGS.values()) {
            CustomisationDiscoveryConfigAccessor custom = (CustomisationDiscoveryConfigAccessor) config;

            Map<String, CustomisationDiscovery> basePrefixes = base.getPrefixes();
            if (basePrefixes == null) {
                basePrefixes = new LinkedHashMap<>();
                base.setPrefixes(basePrefixes);
            }
            mergeStringMap(basePrefixes, custom.getPrefixes(), false);

            Map<String, CustomisationDiscovery> baseSuffixes = base.getSuffixes();
            if (baseSuffixes == null) {
                baseSuffixes = new LinkedHashMap<>();
                base.setSuffixes(baseSuffixes);
            }
            mergeStringMap(baseSuffixes, custom.getSuffixes(), false);

            Map<String, CustomisationDiscovery> baseEmblems = base.getEmblems();
            if (baseEmblems == null) {
                baseEmblems = new LinkedHashMap<>();
                base.setEmblems(baseEmblems);
            }
            mergeStringMap(baseEmblems, custom.getEmblems(), false);

            Map<String, CustomisationDiscovery> baseTrails = base.getTrails();
            if (baseTrails == null) {
                baseTrails = new LinkedHashMap<>();
                base.setTrails(baseTrails);
            }
            mergeStringMap(baseTrails, custom.getTrails(), true);

            Map<ResourceLocation, CustomisationDiscovery> baseGearModels = base.getGearModels();
            if (baseGearModels == null) {
                baseGearModels = new LinkedHashMap<>();
                base.setGearModels(baseGearModels);
            }
            mergeResourceMap(baseGearModels, custom.getGearModels());
        }

        super.afterConfigsLoad(event);
    }

    private static void mergeStringMap(
            Map<String, CustomisationDiscovery> target,
            @Nullable Map<String, CustomisationDiscovery> source,
            boolean lowercaseKeys
    ) {
        if (source == null) {
            return;
        }
        for (Map.Entry<String, CustomisationDiscovery> entry : source.entrySet()) {
            if (entry.getKey() != null && entry.getValue() != null) {
                String key = lowercaseKeys ? entry.getKey().toLowerCase(Locale.ROOT) : entry.getKey();
                target.put(key, entry.getValue());
            }
        }
    }

    private static void mergeResourceMap(
            Map<ResourceLocation, CustomisationDiscovery> target,
            @Nullable Map<ResourceLocation, CustomisationDiscovery> source
    ) {
        if (source == null) {
            return;
        }
        for (Map.Entry<ResourceLocation, CustomisationDiscovery> entry : source.entrySet()) {
            if (entry.getKey() != null && entry.getValue() != null) {
                target.put(entry.getKey(), entry.getValue());
            }
        }
    }
}