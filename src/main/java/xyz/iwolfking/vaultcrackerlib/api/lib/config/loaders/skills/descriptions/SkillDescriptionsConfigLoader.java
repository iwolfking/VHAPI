package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.skills.descriptions;

import com.google.gson.JsonElement;
import iskallia.vault.config.PlayerTitlesConfig;
import iskallia.vault.config.SkillDescriptionsConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.titles.lib.CustomTitleConfig;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.titles.lib.GsonPlayerTitle;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.SkillDescriptionsConfigAccessor;

import java.util.HashMap;
import java.util.Map;

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
