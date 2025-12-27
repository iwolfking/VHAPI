package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.entry.DescriptionData;
import iskallia.vault.config.quest.QuestConfig;
import iskallia.vault.core.vault.QuestManager;
import iskallia.vault.core.vault.Vault;
import iskallia.vault.core.world.storage.VirtualWorld;
import iskallia.vault.quest.base.InVaultQuest;
import iskallia.vault.quest.base.Quest;
import iskallia.vault.quest.type.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.api.util.builder.description.DescriptionDataBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.QuestConfigAccessor;
import xyz.iwolfking.woldsvaults.integration.ftbquests.tasks.CompleteBountyTask;

import java.util.LinkedList;
import java.util.function.Supplier;

public abstract class AbstractQuestProvider extends AbstractVaultConfigDataProvider<AbstractQuestProvider.Builder> {
    protected AbstractQuestProvider(DataGenerator generator, String modid) {
        super(generator, modid, "quests", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Quests Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<QuestConfig> {
        LinkedList<Quest> entries = new LinkedList<>();

        public Builder() {
            super(QuestConfig::new);
        }

        public Builder addQuest(Quest quest) {
            entries.add(quest);
            return this;
        }

        public <T extends Quest> Builder addQuest(String id, String name, TextComponent description, ResourceLocation icon, ResourceLocation targetId, float targetProgress, String unlockedBy, Quest.QuestReward reward, QuestFactory<T> questFactory) {
            entries.add(questFactory.create(id, name, DescriptionData.of(description), icon, targetId, targetProgress, unlockedBy, reward));
            return this;
        }

        public Builder addCompletionQuest(String id, String name, TextComponent description, ResourceLocation icon, ResourceLocation targetId, float targetProgress, String unlockedBy, Quest.QuestReward reward) {
            return addQuest(id, name, description, icon, targetId, targetProgress, unlockedBy, reward, CompleteVaultQuest::new);
        }

        public Builder addEnterQuest(String id, String name, TextComponent description, ResourceLocation icon, ResourceLocation targetId, float targetProgress, String unlockedBy, Quest.QuestReward reward) {
            return addQuest(id, name, description, icon, targetId, targetProgress, unlockedBy, reward, EnterVaultQuest::new);
        }

        public Builder addSurviveQuest(String id, String name, TextComponent description, ResourceLocation icon, ResourceLocation targetId, float targetProgress, String unlockedBy, Quest.QuestReward reward) {
            return addQuest(id, name, description, icon, targetId, targetProgress, unlockedBy, reward, SurviveQuest::new);
        }

        public Builder addAnvilQuest(String id, String name, TextComponent description, ResourceLocation icon, ResourceLocation targetId, float targetProgress, String unlockedBy, Quest.QuestReward reward) {
            return addQuest(id, name, description, icon, targetId, targetProgress, unlockedBy, reward, AnvilQuest::new);
        }

        public Builder addBlockInteractQuest(String id, String name, TextComponent description, ResourceLocation icon, ResourceLocation targetId, float targetProgress, String unlockedBy, Quest.QuestReward reward) {
            return addQuest(id, name, description, icon, targetId, targetProgress, unlockedBy, reward, BlockInteractionQuest::new);
        }

        public Builder addBountyCompleteQuest(String id, String name, TextComponent description, ResourceLocation icon, ResourceLocation targetId, float targetProgress, String unlockedBy, Quest.QuestReward reward) {
            return addQuest(id, name, description, icon, targetId, targetProgress, unlockedBy, reward, BountyCompleteQuest::new);
        }

        public Builder addCheckmarkQuest(String id, String name, TextComponent description, ResourceLocation icon, ResourceLocation targetId, float targetProgress, String unlockedBy, Quest.QuestReward reward) {
            return addQuest(id, name, description, icon, targetId, targetProgress, unlockedBy, reward, CheckmarkQuest::new);
        }

        @Override
        protected void configureConfig(QuestConfig config) {
            ((QuestConfigAccessor)config).setQuests(entries);
        }

    }

    @FunctionalInterface
    public interface QuestFactory<T extends Quest> {
        T create(String id, String name, DescriptionData description, ResourceLocation icon, ResourceLocation targetId, float targetProgress, String unlockedBy, Quest.QuestReward reward);
    }
}
