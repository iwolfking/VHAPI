package xyz.iwolfking.vhapi.api.loaders.skills.descriptions;

import iskallia.vault.config.AbilitiesDescriptionsConfig;

import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.AbilitiesDescriptionsConfigAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.DescriptionDataAccessor;

public class AbilitiesDescriptionsConfigLoader extends VaultConfigProcessor<AbilitiesDescriptionsConfig> {
    public AbilitiesDescriptionsConfigLoader() {
        super(new AbilitiesDescriptionsConfig(), "abilities/descriptions");
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
