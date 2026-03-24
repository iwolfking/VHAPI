package xyz.iwolfking.vhapi.api.loaders.skills;

import iskallia.vault.config.RoyalePresetConfig;
import iskallia.vault.config.SkillScrollConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraftforge.fml.common.Mod;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.SkillScrollConfigAccessor;

public class RoyalePresetsConfigLoader extends VaultConfigProcessor<RoyalePresetConfig> {
    public RoyalePresetsConfigLoader() {
        super(new RoyalePresetConfig(), "royale_presets");
    }


    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(RoyalePresetConfig config : this.CUSTOM_CONFIGS.values()) {
            ModConfigs.PRESET_CONFIG.LEVELS.addAll(config.LEVELS);
            ModConfigs.PRESET_CONFIG.skillPresetSize = config.skillPresetSize;
            ModConfigs.PRESET_CONFIG.blueTrinketPresetSize = config.blueTrinketPresetSize;
            ModConfigs.PRESET_CONFIG.redTrinketPresetSize = config.redTrinketPresetSize;
            super.afterConfigsLoad(event);
        }
    }
}
