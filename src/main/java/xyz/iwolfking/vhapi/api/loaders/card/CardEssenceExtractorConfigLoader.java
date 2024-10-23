package xyz.iwolfking.vhapi.api.loaders.card;

import iskallia.vault.config.CardEssenceExtractorConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.CardEssenceExtractorConfigAccessor;


public class CardEssenceExtractorConfigLoader extends VaultConfigProcessor<CardEssenceExtractorConfig> {
    public CardEssenceExtractorConfigLoader() {
        super(new CardEssenceExtractorConfig(), "card/essence_extractor");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(CardEssenceExtractorConfig config : this.CUSTOM_CONFIGS.values()) {
            ((CardEssenceExtractorConfigAccessor)ModConfigs.CARD_ESSENCE_EXTRACTOR).getTierConfigs().putAll(((CardEssenceExtractorConfigAccessor)config).getTierConfigs());
        }

    }
}
