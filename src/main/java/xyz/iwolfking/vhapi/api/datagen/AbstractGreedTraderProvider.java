package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.greed.GreedChallengeEntry;
import iskallia.vault.config.greed.GreedQuestEntry;
import iskallia.vault.config.greed.GreedTraderConfig;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.ItemStack;
import xyz.iwolfking.vhapi.api.datagen.lib.BasicListBuilder;
import xyz.iwolfking.vhapi.api.datagen.lib.IGenericBuilder;
import xyz.iwolfking.vhapi.api.datagen.lib.ListBuilder;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.GreedChallengeEntryAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.GreedTraderConfigAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.GreedTraderConfigTradeEntryAccessor;

import java.util.*;
import java.util.function.Consumer;

public abstract class AbstractGreedTraderProvider extends AbstractVaultConfigDataProvider<AbstractGreedTraderProvider.Builder> {
    protected AbstractGreedTraderProvider(DataGenerator generator, String modid) {
        super(generator, modid, "greed/trader", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Greed Trader Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<GreedTraderConfig> {
        Map<Integer, List<GreedTraderConfig.TradeEntry>> tradeEntries = new LinkedHashMap<>();
        List<GreedChallengeEntry> challengeEntries = new LinkedList<>();
        List<GreedQuestEntry> questEntries = new LinkedList<>();
        Map<String, List<GreedTraderConfig.WeightedRoomPool>> roomPools = new LinkedHashMap<>();
        Map<String, List<GreedTraderConfig.WeightedModifier>> weightedModifiers = new LinkedHashMap<>();

        public Builder() {
            super(GreedTraderConfig::new);
        }

        public Builder addTradeEntries(Integer greedTier, Consumer<ListBuilder<GreedTraderConfig.TradeEntry, TradeEntryBuilder>> tradeEntryListBuilderConsumer) {
            ListBuilder<GreedTraderConfig.TradeEntry, TradeEntryBuilder> builder = new ListBuilder<>(TradeEntryBuilder::new);
            tradeEntryListBuilderConsumer.accept(builder);
            tradeEntries.put(greedTier, builder.build());
            return this;
        }

        public Builder addChallengeEntry(String name, String description, String challengeCrystalId, int minTier, int maxTier) {
            GreedChallengeEntry entry = new GreedChallengeEntry();
            ((GreedChallengeEntryAccessor)entry).setDisplayName(name);
            ((GreedChallengeEntryAccessor)entry).setDescription(description);
            ((GreedChallengeEntryAccessor)entry).setId(challengeCrystalId);
            ((GreedChallengeEntryAccessor)entry).setMinTier(minTier);
            ((GreedChallengeEntryAccessor)entry).setMaxTier(maxTier);
            challengeEntries.add(entry);
            return this;
        }

        public Builder addGreedQuestEntry(GreedQuestEntry entry) {
            questEntries.add(entry);
            return this;
        }

        public Builder addRoomPool(String type, Consumer<BasicListBuilder<GreedTraderConfig.WeightedRoomPool>> builderConsumer) {
            BasicListBuilder<GreedTraderConfig.WeightedRoomPool> weightedRoomPoolBasicListBuilder = new BasicListBuilder<>();
            builderConsumer.accept(weightedRoomPoolBasicListBuilder);
            roomPools.put(type, weightedRoomPoolBasicListBuilder.build());
            return this;
        }

        public Builder addModifiers(String type, Consumer<BasicListBuilder<GreedTraderConfig.WeightedModifier>> builderConsumer) {
            BasicListBuilder<GreedTraderConfig.WeightedModifier> weightedModifierListBuilder = new BasicListBuilder<>();
            builderConsumer.accept(weightedModifierListBuilder);
            weightedModifiers.put(type, weightedModifierListBuilder.build());
            return this;
        }

        @Override
        protected void configureConfig(GreedTraderConfig config) {
            ((GreedTraderConfigAccessor)config).getTierPoolsMap().putAll(tradeEntries);
            ((GreedTraderConfigAccessor)config).getQuestsList().addAll(questEntries);
            ((GreedTraderConfigAccessor)config).getChallengesList().addAll(challengeEntries);
            ((GreedTraderConfigAccessor)config).getRoomPoolsByType().putAll(roomPools);
            ((GreedTraderConfigAccessor)config).getModifierPools().putAll(weightedModifiers);
        }
    }

    public static class TradeEntryBuilder implements IGenericBuilder<GreedTraderConfig.TradeEntry> {
        private String type;
        private Integer weight;
        private ItemStack itemStack;
        private Integer minAmount;
        private Integer maxAmount;
        private Integer minCoinCost;
        private Integer maxCoinCost;
        private String poolId;

        public GreedTraderConfig.TradeEntry item(int weight, ItemStack stack, int minAmount, int maxAmount, int minCoinCost, int maxCoinCost) {
            this.type = "item";
            this.weight = weight;
            this.itemStack = stack;
            this.minAmount = minAmount;
            this.maxAmount = maxAmount;
            this.minCoinCost = minCoinCost;
            this.maxCoinCost = maxCoinCost;
            return build();
        }

        public GreedTraderConfig.TradeEntry randomEtching(int weight, int minCoinCost, int maxCoinCost) {
            this.type = "random_etching";
            this.weight = weight;
            this.minCoinCost = minCoinCost;
            this.maxCoinCost = maxCoinCost;
            return build();
        }

        public GreedTraderConfig.TradeEntry pool(int weight, String poolId, int minCoinCost, int maxCoinCost) {
            this.type = "pool";
            this.poolId = poolId;
            this.weight = weight;
            this.minCoinCost = minCoinCost;
            this.maxCoinCost = maxCoinCost;
            return build();
        }

        public GreedTraderConfig.TradeEntry xpBurger(int weight, String poolId, int minAmount, int maxAmount,  int minCoinCost, int maxCoinCost) {
            this.type = "xp_burger";
            this.weight = weight;
            this.minCoinCost = minCoinCost;
            this.maxCoinCost = maxCoinCost;
            this.minAmount = minAmount;
            this.maxAmount = maxAmount;
            return build();
        }

        @Override
        public GreedTraderConfig.TradeEntry build() {
            GreedTraderConfig.TradeEntry entry = new GreedTraderConfig.TradeEntry();
            if(type != null) {
                ((GreedTraderConfigTradeEntryAccessor)entry).setType(type);
            }
            if(weight != null) {
                ((GreedTraderConfigTradeEntryAccessor)entry).setWeight(weight);
            }
            if(itemStack != null) {
                ((GreedTraderConfigTradeEntryAccessor)entry).setItemStack(itemStack);
            }
            if(minAmount != null) {
                ((GreedTraderConfigTradeEntryAccessor)entry).setMinAmount(minAmount);
            }
            if(maxAmount != null) {
                ((GreedTraderConfigTradeEntryAccessor)entry).setMaxAmount(maxAmount);
            }
            if(minCoinCost != null) {
                ((GreedTraderConfigTradeEntryAccessor)entry).setMinCoinCost(minCoinCost);
            }
            if(maxCoinCost != null) {
                ((GreedTraderConfigTradeEntryAccessor)entry).setMaxCoinCost(maxCoinCost);
            }
            if(poolId != null) {
                ((GreedTraderConfigTradeEntryAccessor)entry).setPoolId(poolId);
            }

            return entry;
        }
    }
}
