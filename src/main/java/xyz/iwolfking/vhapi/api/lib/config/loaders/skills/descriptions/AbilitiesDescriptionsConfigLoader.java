package xyz.iwolfking.vhapi.api.lib.config.loaders.skills.descriptions;

import iskallia.vault.config.AbilitiesDescriptionsConfig;

import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vhapi.mixin.accessors.AbilitiesDescriptionsConfigAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.DescriptionDataAccessor;

import java.util.HashMap;
public class AbilitiesDescriptionsConfigLoader extends VaultConfigDataLoader<AbilitiesDescriptionsConfig> {
    public AbilitiesDescriptionsConfigLoader(String namespace) {
        super(new AbilitiesDescriptionsConfig(), "abilities/descriptions", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(AbilitiesDescriptionsConfig config : this.CUSTOM_CONFIGS.values()) {
            for(AbilitiesDescriptionsConfig.DescriptionData element : ((AbilitiesDescriptionsConfigAccessor)config).getData().values()) {
                ModConfigs.COLORS.replaceColorStrings(((DescriptionDataAccessor)element).getDescription());
            }

            ((AbilitiesDescriptionsConfigAccessor)ModConfigs.ABILITIES_DESCRIPTIONS).getData().putAll(((AbilitiesDescriptionsConfigAccessor) config).getData());
        }
    }
}
