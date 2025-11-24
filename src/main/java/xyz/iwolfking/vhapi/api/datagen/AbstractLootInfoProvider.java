package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.LegacyLootTablesConfig;
import iskallia.vault.config.LootInfoConfig;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.LegacyLootTablesConfigAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.LootInfoConfigAccessor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class AbstractLootInfoProvider extends AbstractVaultConfigDataProvider<AbstractLootInfoProvider.Builder> {
    protected AbstractLootInfoProvider(DataGenerator generator, String modid) {
        super(generator, modid, "loot_info", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Loot Info Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<LootInfoConfig> {
        private final Set<ResourceLocation> excludeFromTooltipSet = new HashSet<>();

        private final Map<ResourceLocation, LootInfoConfig.LootInfo> lootInfoMap = new HashMap<>();

        public Builder() {
            super(LootInfoConfig::new);
        }

        public Builder exclude(ResourceLocation excludeId) {
            excludeFromTooltipSet.add(excludeId);
            return this;
        }

        public Builder lootInfo(ResourceLocation id, String display, Consumer<Map<ResourceLocation, Integer>> lootTableKeysConsumer) {
            Map<ResourceLocation, Integer> lootTableKeysMap = new HashMap<>();
            Map<ResourceLocation, LootInfoConfig.LootTableData> lootTableDataHashMap = new HashMap<>();
            lootTableKeysConsumer.accept(lootTableKeysMap);
            lootTableKeysMap.forEach((resourceLocation, integer) -> lootTableDataHashMap.put(resourceLocation, new LootInfoConfig.LootTableData(integer)));
            lootInfoMap.put(id, new LootInfoConfig.LootInfo(display, lootTableDataHashMap));
            return this;
        }

        @Override
        protected void configureConfig(LootInfoConfig config) {
            ((LootInfoConfigAccessor)config).getLootInfoMapModifiable().putAll(lootInfoMap);
            ((LootInfoConfigAccessor)config).getExcludeFromTooltipSet().addAll(excludeFromTooltipSet);
        }
    }
}
