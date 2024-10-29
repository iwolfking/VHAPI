package xyz.iwolfking.vhapi.api.loaders.card;


import iskallia.vault.config.card.CardTasksConfig;
import iskallia.vault.init.ModConfigs;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.CardTasksConfigAccessor;

public class CardTasksConfigLoader extends VaultConfigProcessor<CardTasksConfig> {
    public CardTasksConfigLoader() {
        super(new CardTasksConfig(), "card/tasks");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(ResourceLocation key : this.CUSTOM_CONFIGS.keySet()) {
            CardTasksConfig config = CUSTOM_CONFIGS.get(key);
            ((CardTasksConfigAccessor) ModConfigs.CARD_TASKS).getValues().putAll(((CardTasksConfigAccessor)config).getValues());
            if(key.getPath().contains("overwrite")) {
                ModConfigs.CARD_TASKS = config;
            }
            else if(key.getPath().contains("remove")) {
                for(String groupKey : ((CardTasksConfigAccessor) config).getPools().keySet()) {
                    if(((CardTasksConfigAccessor) ModConfigs.CARD_TASKS).getPools().containsKey(groupKey)) {

                        ((CardTasksConfigAccessor) config).getPools().get(groupKey).forEach((s, aDouble) -> {
                            ((CardTasksConfigAccessor) ModConfigs.CARD_TASKS).getPools().get(groupKey).remove(s);
                        });
                    }
                }
            }
            else if(key.getPath().contains("replace")) {
                for(String groupKey : ((CardTasksConfigAccessor) config).getPools().keySet()) {
                    if(((CardTasksConfigAccessor) ModConfigs.CARD_TASKS).getPools().containsKey(groupKey)) {

                        ((CardTasksConfigAccessor) config).getPools().get(groupKey).forEach((s, aDouble) -> {
                            ((CardTasksConfigAccessor) ModConfigs.CARD_TASKS).getPools().get(groupKey).replace(s, aDouble);
                        });
                    }
                }
            }
            else {
                for(String groupKey : ((CardTasksConfigAccessor) config).getPools().keySet()) {
                    if(((CardTasksConfigAccessor) ModConfigs.CARD_TASKS).getPools().containsKey(groupKey)) {
                        ((CardTasksConfigAccessor) config).getPools().get(groupKey).forEach((s, aDouble) -> {
                            ((CardTasksConfigAccessor) ModConfigs.CARD_TASKS).getPools().get(groupKey).add(s, aDouble);
                        });
                    }
                    else {
                        ((CardTasksConfigAccessor) ModConfigs.CARD_TASKS).getPools().put(groupKey, ((CardTasksConfigAccessor) config).getPools().get(groupKey));
                    }
                }
            }
        }
        super.afterConfigsLoad(event);
    }
}
