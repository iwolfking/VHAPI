package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.quest.QuestConfig;
import iskallia.vault.quest.base.Quest;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = QuestConfig.class, remap = false)
public interface QuestConfigAccessor {
    @Accessor("quests")
    List<Quest> getQuests();

    @Accessor("quests")
    void setQuests(List<Quest> quests);
}
