package xyz.iwolfking.vhapi.api.lib.config.loaders.card;

import iskallia.vault.config.CardEssenceExtractorConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vhapi.mixin.accessors.CardEssenceExtractorConfigAccessor;
import java.util.HashMap;


public class CardEssenceExtractorConfigLoader extends VaultConfigDataLoader<CardEssenceExtractorConfig> {
    public CardEssenceExtractorConfigLoader(String namespace) {
        super(new CardEssenceExtractorConfig(), "card/essence_extractor", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(CardEssenceExtractorConfig config : this.CUSTOM_CONFIGS.values()) {
            ((CardEssenceExtractorConfigAccessor)ModConfigs.CARD_ESSENCE_EXTRACTOR).getTierConfigs().putAll(((CardEssenceExtractorConfigAccessor)config).getTierConfigs());
        }

    }
}
