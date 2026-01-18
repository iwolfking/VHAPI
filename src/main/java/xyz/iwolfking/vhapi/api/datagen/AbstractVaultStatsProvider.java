package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.VaultStatsConfig;
import iskallia.vault.core.vault.player.Completion;
import iskallia.vault.core.vault.stat.VaultChestType;
import iskallia.vault.util.VaultRarity;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.VaultStatsConfigAccessor;

import java.util.*;
import java.util.function.Consumer;

public abstract class AbstractVaultStatsProvider extends AbstractVaultConfigDataProvider<AbstractVaultStatsProvider.Builder> {
    protected AbstractVaultStatsProvider(DataGenerator generator, String modid) {
        super(generator, modid, "vault/stats", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Vault Stats Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<VaultStatsConfig> {
        private final Map<VaultChestType, Map<VaultRarity, Float>> chests = new LinkedHashMap<>();
        private final Map<VaultChestType, Map<VaultRarity, Float>> barrels = new LinkedHashMap<>();
        private final Map<ResourceLocation, Float> blocksMined = new LinkedHashMap<>();
        private final Map<ResourceLocation, Float> mobsKilled = new LinkedHashMap<>();
        private final Map<String, Map<Completion, Float>> completion = new LinkedHashMap<>();


        public Builder() {
            super(VaultStatsConfig::new);
        }

        public Builder addChest(VaultChestType type, Consumer<Map<VaultRarity, Float>> values) {
            Map<VaultRarity, Float> chestMap = new HashMap<>();
            values.accept(chestMap);
            chests.put(type, chestMap);
            return this;
        }

        public Builder addBarrel(VaultChestType type, Consumer<Map<VaultRarity, Float>> values) {
            Map<VaultRarity, Float> chestMap = new HashMap<>();
            values.accept(chestMap);
            barrels.put(type, chestMap);
            return this;
        }

        public Builder addBlockMined(Consumer<Map<ResourceLocation, Float>> values) {
            Map<ResourceLocation, Float> chestMap = new HashMap<>();
            values.accept(chestMap);
            blocksMined.putAll(chestMap);
            return this;
        }

        public Builder addMobsKilled(Consumer<Map<ResourceLocation, Float>> values) {
            Map<ResourceLocation, Float> chestMap = new HashMap<>();
            values.accept(chestMap);
            mobsKilled.putAll(chestMap);
            return this;
        }

        public Builder addCompletion(String objective, Consumer<Map<Completion, Float>> values) {
            Map<Completion, Float> chestMap = new HashMap<>();
            values.accept(chestMap);
            completion.put(objective, chestMap);
            return this;
        }

        @Override
        protected void configureConfig(VaultStatsConfig config) {
            ((VaultStatsConfigAccessor)config).setChestsMap(chests);
            ((VaultStatsConfigAccessor)config).setBarrelsMap(barrels);
            ((VaultStatsConfigAccessor)config).setBlocksMinedMap(blocksMined);
            ((VaultStatsConfigAccessor)config).setMobsKilledMap(mobsKilled);
            ((VaultStatsConfigAccessor)config).setCompletionMap(completion);
        }
    }
}
