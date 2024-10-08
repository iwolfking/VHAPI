package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.card;

import iskallia.vault.config.card.BoosterPackConfig;
import iskallia.vault.config.card.CardModifiersConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.CardModifiersConfigAccessor;

import java.util.HashMap;
import java.util.Map;

public class CardModifiersConfigLoader extends VaultConfigDataLoader<CardModifiersConfig> {
    public CardModifiersConfigLoader(String namespace) {
        super(new CardModifiersConfig(), "card/modifiers", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ResourceLocation key : this.CUSTOM_CONFIGS.keySet()) {
            CardModifiersConfig config = CUSTOM_CONFIGS.get(key);
            ((CardModifiersConfigAccessor)ModConfigs.CARD_MODIFIERS).getValues().putAll(((CardModifiersConfigAccessor)config).getValues());
            if(key.getPath().contains("overwrite")) {
                ModConfigs.CARD_MODIFIERS = config;
            }
            else if(key.getPath().contains("remove")) {
                for(String groupKey : ((CardModifiersConfigAccessor) config).getPools().keySet()) {
                    if(((CardModifiersConfigAccessor) ModConfigs.CARD_MODIFIERS).getPools().containsKey(groupKey)) {

                        ((CardModifiersConfigAccessor) config).getPools().get(groupKey).forEach((s, aDouble) -> {
                            ((CardModifiersConfigAccessor) ModConfigs.CARD_MODIFIERS).getPools().get(groupKey).remove(s);
                        });
                    }
                }
            }
            else if(key.getPath().contains("replace")) {
                for(String groupKey : ((CardModifiersConfigAccessor) config).getPools().keySet()) {
                    if(((CardModifiersConfigAccessor) ModConfigs.CARD_MODIFIERS).getPools().containsKey(groupKey)) {

                        ((CardModifiersConfigAccessor) config).getPools().get(groupKey).forEach((s, aDouble) -> {
                            ((CardModifiersConfigAccessor) ModConfigs.CARD_MODIFIERS).getPools().get(groupKey).replace(s, aDouble);
                        });
                    }
                }
            }
            else {
                for(String groupKey : ((CardModifiersConfigAccessor) config).getPools().keySet()) {
                    if(((CardModifiersConfigAccessor) ModConfigs.CARD_MODIFIERS).getPools().containsKey(groupKey)) {
                        ((CardModifiersConfigAccessor) config).getPools().get(groupKey).forEach((s, aDouble) -> {
                            ((CardModifiersConfigAccessor) ModConfigs.CARD_MODIFIERS).getPools().get(groupKey).add(s, aDouble);
                        });
                    }
                }
            }
        }

    }
}