package xyz.iwolfking.vhapi.api.loaders.card;

import iskallia.vault.config.card.CardDeckConfig;
import iskallia.vault.config.card.DeckModifiersConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.CardDeckConfigAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.DeckModifiersConfigAccessor;


public class DeckModifiersConfigLoader extends VaultConfigProcessor<DeckModifiersConfig> {
    public DeckModifiersConfigLoader() {
        super(new DeckModifiersConfig(), "card/deck_mods");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        this.CUSTOM_CONFIGS.forEach((resourceLocation, deckModifiersConfig) -> {
            ModConfigs.DECK_MODIFIERS.getValues().putAll(deckModifiersConfig.getValues());
            ((DeckModifiersConfigAccessor)deckModifiersConfig).getPools().forEach((pool, values) -> {
                if(((DeckModifiersConfigAccessor)ModConfigs.DECK_MODIFIERS).getPools().containsKey(pool)) {
                    ((DeckModifiersConfigAccessor) deckModifiersConfig).getPools().get(pool).forEach((value, weight) -> {
                        ((DeckModifiersConfigAccessor)ModConfigs.DECK_MODIFIERS).getPools().get(pool).add(value, weight);
                    });
                }
                else {
                    ((DeckModifiersConfigAccessor)ModConfigs.DECK_MODIFIERS).getPools().put(pool, values);
                }
            });
        });
    }
}
