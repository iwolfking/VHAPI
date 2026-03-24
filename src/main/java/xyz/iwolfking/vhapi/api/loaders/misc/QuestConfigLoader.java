package xyz.iwolfking.vhapi.api.loaders.misc;

import iskallia.vault.config.quest.QuestConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.QuestConfigAccessor;

public class QuestConfigLoader extends VaultConfigProcessor<QuestConfig> {
    public QuestConfigLoader() {
        super(new QuestConfig(), "quests");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        this.CUSTOM_CONFIGS.forEach(((resourceLocation, questConfig) -> {
            ((QuestConfigAccessor)ModConfigs.QUESTS).getQuests().addAll(questConfig.getQuests());
            ((QuestConfigAccessor)ModConfigs.SKY_QUESTS).getQuests().addAll(questConfig.getQuests());
        }));
        super.afterConfigsLoad(event);
    }
}
