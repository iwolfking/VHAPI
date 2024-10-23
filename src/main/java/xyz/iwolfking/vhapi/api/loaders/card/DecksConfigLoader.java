package xyz.iwolfking.vhapi.api.loaders.card;

import iskallia.vault.config.card.CardDeckConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.CardDeckConfigAccessor;


public class DecksConfigLoader extends VaultConfigProcessor<CardDeckConfig> {
    public DecksConfigLoader() {
        super(new CardDeckConfig(), "card/decks");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(CardDeckConfig config : this.CUSTOM_CONFIGS.values()) {
            ((CardDeckConfigAccessor) ModConfigs.CARD_DECK).getValues().putAll(((CardDeckConfigAccessor)config).getValues());
        }

    }
}
