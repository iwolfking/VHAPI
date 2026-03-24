package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.tool.ToolPulverizingConfig;
import iskallia.vault.core.world.loot.LootTable;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.ToolPulverizingConfigAccessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class AbstractToolPulverizingProvider extends AbstractVaultConfigDataProvider {
    protected AbstractToolPulverizingProvider(DataGenerator generator, String modid) {
        super(generator, modid, "tool/pulverizing", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Tool Pulverizing Provider";
    }

    public static class Builder extends VaultConfigBuilder<ToolPulverizingConfig> {
        private final Map<ResourceLocation, List<LootTable.Entry>> loot = new HashMap<>();

        public Builder() {
            super(ToolPulverizingConfig::new);
        }

        public Builder add(ResourceLocation blockName, Consumer<List<LootTable.Entry>> entriesConsumer) {
            List<LootTable.Entry> entries = new ArrayList<>();
            entriesConsumer.accept(entries);
            loot.put(blockName, entries);
            return this;
        }

        @Override
        protected void configureConfig(ToolPulverizingConfig config) {
            ((ToolPulverizingConfigAccessor)config).setLoot(loot);
        }

    }
}
