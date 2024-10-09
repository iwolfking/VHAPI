package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.skills.descriptions;

import com.google.gson.JsonElement;
import iskallia.vault.config.AbilitiesDescriptionsConfig;

import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.AbilitiesDescriptionsConfigAccessor;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.DescriptionDataAccessor;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.SkillDescriptionsConfigAccessor;

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
