package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.TooltipConfig;
import iskallia.vault.config.tool.ToolPulverizingConfig;
import iskallia.vault.core.world.loot.LootTable;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.mixin.accessors.TooltipConfigAccessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractToolPulverizingProvider extends AbstractVaultConfigDataProvider {
    protected AbstractToolPulverizingProvider(DataGenerator generator, String modid) {
        super(generator, modid, "tool/pulverizing");
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Tool Pulverizing Provider";
    }

    public static class ToolPulverizingConfigBuilder {
        private Map<ResourceLocation, List<LootTable.Entry>> loot = new HashMap<>();

        public ToolPulverizingConfigBuilder add(ResourceLocation blockName, Consumer<List<LootTable.Entry>> entriesConsumer) {
            List<LootTable.Entry> entries = new ArrayList<>();
            entriesConsumer.accept(entries);
            loot.put(blockName, entries);
            return this;
        }

        public ToolPulverizingConfig build() {
            ToolPulverizingConfig newConfig = new ToolPulverizingConfig();
            newConfig.getLoot().putAll(loot);
            return newConfig;
        }

    }
}
