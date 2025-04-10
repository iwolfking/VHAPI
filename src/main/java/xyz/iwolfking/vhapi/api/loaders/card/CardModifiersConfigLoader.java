package xyz.iwolfking.vhapi.api.loaders.card;


import iskallia.vault.config.card.CardModifiersConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.CardModifiersConfigAccessor;

public class CardModifiersConfigLoader extends VaultConfigProcessor<CardModifiersConfig> {
    public CardModifiersConfigLoader() {
        super(new CardModifiersConfig(), "card/modifiers");
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
                    else {
                        ((CardModifiersConfigAccessor) ModConfigs.CARD_MODIFIERS).getPools().put(groupKey, ((CardModifiersConfigAccessor) config).getPools().get(groupKey));
                    }
                }
            }
        }
        super.afterConfigsLoad(event);
    }
}
