package xyz.iwolfking.vhapi.api.loaders.general;

import iskallia.vault.config.TooltipConfig;
import iskallia.vault.config.TranslationsConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.TooltipConfigAccessor;

import java.util.HashSet;
import java.util.Set;

public class TranslationsConfigLoader extends VaultConfigProcessor<TranslationsConfig> {
    public TranslationsConfigLoader() {
        super(new TranslationsConfig(), "translations");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(TranslationsConfig config : this.CUSTOM_CONFIGS.values()) {
            ModConfigs.TRANSLATIONS.getTranslations().putAll(config.getTranslations());
        }
        super.afterConfigsLoad(event);
    }
}
