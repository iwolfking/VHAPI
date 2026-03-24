package xyz.iwolfking.vhapi.api.datagen.bounty;

import iskallia.vault.config.TooltipConfig;
import iskallia.vault.config.bounty.RewardConfig;
import iskallia.vault.config.entry.IntRangeEntry;
import iskallia.vault.config.entry.ItemStackPool;
import iskallia.vault.config.entry.LevelEntryMap;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import xyz.iwolfking.vhapi.api.datagen.AbstractVaultConfigDataProvider;
import xyz.iwolfking.vhapi.api.datagen.lib.BasicListBuilder;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.TooltipConfigAccessor;

import java.util.*;
import java.util.function.Consumer;

public abstract class AbstractBountyRewardsProvider extends AbstractVaultConfigDataProvider<AbstractBountyRewardsProvider.Builder> {
    protected AbstractBountyRewardsProvider(DataGenerator generator, String modid) {
        super(generator, modid, "bounty/rewards", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Bounty Rewards Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<RewardConfig> {
        LinkedHashMap<String, LevelEntryMap<RewardConfig.RewardEntry>> entryMap = new LinkedHashMap<>();

        public Builder() {
            super(RewardConfig::new);
        }

        public Builder addRewardPool(String key, int level, IntRangeEntry vaultExp, ItemStackPool pool, List<ResourceLocation> discoverModels) {
            RewardConfig.RewardEntry entry = new RewardConfig.RewardEntry(vaultExp, pool, discoverModels);
            LevelEntryMap<RewardConfig.RewardEntry> levelEntryMap = new LevelEntryMap<>();
            levelEntryMap.put(level, entry);
            entryMap.put(key, levelEntryMap);
            return this;
        }

        public Builder addRewardPool(String key, int level, IntRangeEntry vaultExp, Consumer<ItemPoolBuilder> poolConsumer, Consumer<List<ResourceLocation>> discoverModels) {
            List<ResourceLocation> models = new ArrayList<>();
            discoverModels.accept(models);
            ItemPoolBuilder builder = new ItemPoolBuilder();
            poolConsumer.accept(builder);
            return addRewardPool(key, level, vaultExp, builder.build(), models);
        }

        public Builder addRewardPool(String key, int level, IntRangeEntry vaultExp, Consumer<ItemPoolBuilder> poolConsumer) {
            return addRewardPool(key, level, vaultExp, poolConsumer, resourceLocations -> {});
        }

        @Override
        protected void configureConfig(RewardConfig config) {
            config.POOLS = entryMap;
        }
    }

    public static class ItemPoolBuilder {
        ItemStackPool pool = ItemStackPool.EMPTY;

        public ItemPoolBuilder addStack(ItemStack stack, int min, int max, int weight) {
            pool.addItemStack(stack, min, max, weight);
            return this;
        }

        public ItemPoolBuilder addStack(ItemLike item, int min, int max, int weight) {
            pool.addItemStack(new ItemStack(item), min, max, weight);
            return this;
        }

        public ItemStackPool build() {
            return pool;
        }
    }
}
