package xyz.iwolfking.vhapi.api.lib.config.loaders.skills.descriptions;

import com.google.gson.JsonElement;
import iskallia.vault.config.SkillDescriptionsConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vhapi.mixin.accessors.SkillDescriptionsConfigAccessor;

import java.util.HashMap;

public class SkillDescriptionsConfigLoader extends VaultConfigDataLoader<SkillDescriptionsConfig> {
    public SkillDescriptionsConfigLoader(String namespace) {
        super(new SkillDescriptionsConfig(), "skill/descriptions", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(SkillDescriptionsConfig config : this.CUSTOM_CONFIGS.values()) {
            for(JsonElement element : ((SkillDescriptionsConfigAccessor)config).getDescriptions().values()) {
                ModConfigs.COLORS.replaceColorStrings(element);
            }

            ((SkillDescriptionsConfigAccessor)ModConfigs.SKILL_DESCRIPTIONS).getDescriptions().putAll(((SkillDescriptionsConfigAccessor) config).getDescriptions());
        }
    }

}
