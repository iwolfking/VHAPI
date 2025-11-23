package xyz.iwolfking.vhapi.api.datagen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import iskallia.vault.config.LootInfoConfig;
import iskallia.vault.config.ModBoxConfig;
import iskallia.vault.config.entry.vending.ProductEntry;
import iskallia.vault.util.data.WeightedList;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public abstract class AbstractLootInfoProvider extends AbstractVaultConfigDataProvider {
    protected AbstractLootInfoProvider(DataGenerator generator, String modid) {
        super(generator, modid, "loot_info");
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Loot Info Data Provider";
    }

    public static class LootInfoConfigBuilder {
        private final Set<ResourceLocation> excludeFromTooltipSet = new HashSet<>();

        private final Map<ResourceLocation, LootInfoConfig.LootInfo> lootInfoMap = new HashMap<>();

        public LootInfoConfigBuilder exclude(ResourceLocation excludeId) {
            excludeFromTooltipSet.add(excludeId);
            return this;
        }

        public LootInfoConfigBuilder lootInfo(ResourceLocation id, String display, Consumer<Map<ResourceLocation, Integer>> lootTableKeysConsumer) {
            Map<ResourceLocation, Integer> lootTableKeysMap = new HashMap<>();
            Map<ResourceLocation, LootInfoConfig.LootTableData> lootTableDataHashMap = new HashMap<>();
            lootTableKeysConsumer.accept(lootTableKeysMap);
            lootTableKeysMap.forEach((resourceLocation, integer) -> lootTableDataHashMap.put(resourceLocation, new LootInfoConfig.LootTableData(integer)));
            lootInfoMap.put(id, new LootInfoConfig.LootInfo(display, lootTableDataHashMap));
            return this;
        }

        public LootInfoConfig build() {
            LootInfoConfig newConfig = new LootInfoConfig();
            newConfig.getLootInfoMap().putAll(lootInfoMap);
            return newConfig;
        }

    }
}
