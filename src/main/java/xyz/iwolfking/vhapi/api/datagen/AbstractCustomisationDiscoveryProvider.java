package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.customisation.CustomisationDiscovery;
import iskallia.vault.config.customisation.CustomisationDiscoveryConfig;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.CustomisationDiscoveryConfigAccessor;

public abstract class AbstractCustomisationDiscoveryProvider
        extends AbstractVaultConfigDataProvider<AbstractCustomisationDiscoveryProvider.Builder> {
    protected AbstractCustomisationDiscoveryProvider(DataGenerator generator, String modid) {
        super(generator, modid, "customisation_discovery", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Customisation Discovery Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<CustomisationDiscoveryConfig> {
        public final Map<String, CustomisationDiscovery> prefixes = new LinkedHashMap<>();
        public final Map<String, CustomisationDiscovery> suffixes = new LinkedHashMap<>();
        public final Map<String, CustomisationDiscovery> emblems = new LinkedHashMap<>();
        public final Map<String, CustomisationDiscovery> trails = new LinkedHashMap<>();
        public final Map<ResourceLocation, CustomisationDiscovery> gearModels = new LinkedHashMap<>();

        public Builder() {
            super(CustomisationDiscoveryConfig::new);
        }

        public Builder putPrefix(String prefixId, CustomisationDiscovery discovery) {
            this.prefixes.put(prefixId, discovery);
            return this;
        }

        public Builder putSuffix(String suffixId, CustomisationDiscovery discovery) {
            this.suffixes.put(suffixId, discovery);
            return this;
        }

        public Builder putEmblem(String emblemName, CustomisationDiscovery discovery) {
            this.emblems.put(emblemName, discovery);
            return this;
        }

        public Builder putTrail(String trailId, CustomisationDiscovery discovery) {
            this.trails.put(trailId.toLowerCase(Locale.ROOT), discovery);
            return this;
        }

        public Builder putGearModel(ResourceLocation modelId, CustomisationDiscovery discovery) {
            this.gearModels.put(modelId, discovery);
            return this;
        }

        @Override
        protected void configureConfig(CustomisationDiscoveryConfig config) {
            CustomisationDiscoveryConfigAccessor accessor = (CustomisationDiscoveryConfigAccessor) config;
            accessor.setPrefixes(new LinkedHashMap<>(this.prefixes));
            accessor.setSuffixes(new LinkedHashMap<>(this.suffixes));
            accessor.setEmblems(new LinkedHashMap<>(this.emblems));
            accessor.setTrails(new LinkedHashMap<>(this.trails));
            accessor.setGearModels(new LinkedHashMap<>(this.gearModels));
        }
    }
}