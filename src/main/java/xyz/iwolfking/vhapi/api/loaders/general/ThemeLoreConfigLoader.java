package xyz.iwolfking.vhapi.api.loaders.general;

import iskallia.vault.config.ThemeAugmentLoreConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

public class ThemeLoreConfigLoader extends VaultConfigProcessor<ThemeAugmentLoreConfig> {
    public ThemeLoreConfigLoader() {
        super(new ThemeAugmentLoreConfig(), "theme_lore");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ResourceLocation configKey : this.CUSTOM_CONFIGS.keySet()) {
            ThemeAugmentLoreConfig config = this.CUSTOM_CONFIGS.get(configKey);
            config.augments.forEach((s, resourceLocations) -> {
                if(ModConfigs.THEME_AUGMENT_LORE.augments.containsKey(s)) {
                    ModConfigs.THEME_AUGMENT_LORE.augments.get(s).addAll(config.augments.get(s));
                }
                else {
                    ModConfigs.THEME_AUGMENT_LORE.augments.put(s, resourceLocations);
                }
            });
            ModConfigs.THEME_AUGMENT_LORE.series.putAll(config.series);
        }
        super.afterConfigsLoad(event);
    }
}
