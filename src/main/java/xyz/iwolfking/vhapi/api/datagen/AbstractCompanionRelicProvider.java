package xyz.iwolfking.vhapi.api.datagen;

import com.google.gson.annotations.Expose;
import iskallia.vault.config.CompanionRelicsConfig;
import iskallia.vault.config.TooltipConfig;
import iskallia.vault.config.entry.LevelEntryList;
import iskallia.vault.core.util.WeightedList;
import iskallia.vault.util.VaultRarity;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.api.datagen.lib.WeightedLevelEntryListBuilder;
import xyz.iwolfking.vhapi.api.datagen.lib.WeightedListBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.CompanionRelicsConfigAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.TooltipConfigAccessor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractCompanionRelicProvider extends AbstractVaultConfigDataProvider<AbstractCompanionRelicProvider.Builder> {
    protected AbstractCompanionRelicProvider(DataGenerator generator, String modid) {
        super(generator, modid, "companion_relics", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Companion Relics Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<CompanionRelicsConfig> {
        @Expose
        public Map<ResourceLocation, LevelEntryList<CompanionRelicsConfig.Pool>> pools = new LinkedHashMap();
        @Expose
        private Map<Block, Map<VaultRarity, Double>> relicChances = new LinkedHashMap();
        @Expose
        private Map<Block, Map<VaultRarity, Double>> particleChances = new LinkedHashMap();
        @Expose
        private int lootMinLevel = 50;
        @Expose
        private WeightedList<ResourceLocation> lootPoolWeights = new WeightedList<>();

        public Builder() {
            super(CompanionRelicsConfig::new);
        }

        public Builder addPool(ResourceLocation poolName, Consumer<WeightedLevelEntryListBuilder<CompanionRelicsConfig.Pool, CompanionRelicsConfig.Entry>> builderConsumer) {
            WeightedLevelEntryListBuilder<CompanionRelicsConfig.Pool, CompanionRelicsConfig.Entry> builder = new WeightedLevelEntryListBuilder<>(CompanionRelicsConfig.Pool::new);
            builderConsumer.accept(builder);
            pools.put(poolName, builder.build());
            return this;
        }

        public Builder relicChances(Block block, double commonChance, double rareChance, double epicChance, double omegaChance) {
            relicChances.put(block, Map.of(VaultRarity.COMMON, commonChance, VaultRarity.RARE, rareChance, VaultRarity.EPIC, epicChance, VaultRarity.OMEGA, omegaChance));
            return this;
        }

        public Builder particleChances(Block block, double commonChance, double rareChance, double epicChance, double omegaChance) {
            particleChances.put(block, Map.of(VaultRarity.COMMON, commonChance, VaultRarity.RARE, rareChance, VaultRarity.EPIC, epicChance, VaultRarity.OMEGA, omegaChance));
            return this;
        }

        public Builder relicChances(ResourceLocation block, double commonChance, double rareChance, double epicChance, double omegaChance) {
            return relicChances(ForgeRegistries.BLOCKS.getValue(block), commonChance, rareChance, epicChance, omegaChance);
        }

        public Builder particleChances(ResourceLocation block, double commonChance, double rareChance, double epicChance, double omegaChance) {
            return particleChances(ForgeRegistries.BLOCKS.getValue(block), commonChance, rareChance, epicChance, omegaChance);
        }


        public Builder lootMinLevel(int level) {
            lootMinLevel = level;
            return this;
        }

        public Builder lootPoolWeights(Consumer<WeightedListBuilder<ResourceLocation>> builderConsumer) {
            WeightedListBuilder<ResourceLocation> builder = new WeightedListBuilder<>();
            builderConsumer.accept(builder);
            builder.build().forEach(lootPoolWeights::add);
            return this;
        }

        @Override
        protected void configureConfig(CompanionRelicsConfig config) {
            config.pools.putAll(pools);
            ((CompanionRelicsConfigAccessor)config).getRelicChances().putAll(relicChances);
            ((CompanionRelicsConfigAccessor)config).getParticleChances().putAll(particleChances);
            ((CompanionRelicsConfigAccessor)config).setLootPoolWeights(lootPoolWeights);
            ((CompanionRelicsConfigAccessor) config).setLootMinLevel(lootMinLevel);
        }
    }
}
