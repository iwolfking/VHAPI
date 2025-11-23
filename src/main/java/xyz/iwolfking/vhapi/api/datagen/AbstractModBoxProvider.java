package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.ModBoxConfig;
import iskallia.vault.config.TooltipConfig;
import iskallia.vault.config.entry.vending.ProductEntry;
import iskallia.vault.util.data.WeightedList;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.mixin.accessors.TooltipConfigAccessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractModBoxProvider extends AbstractVaultConfigDataProvider {
    protected AbstractModBoxProvider(DataGenerator generator, String modid) {
        super(generator, modid, "loot_box/mod_box");
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Mod Box Data Provider";
    }

    public static class ModBoxConfigBuilder {
        public Map<String, WeightedList<ProductEntry>> POOL = new HashMap<>();

        public ModBoxConfigBuilder addPool(String researchName, Consumer<WeightedList<ProductEntry>> poolConsumer) {
            WeightedList<ProductEntry> pool = new WeightedList<>();
            poolConsumer.accept(pool);
            POOL.put(researchName, pool);
            return this;
        }

        public ModBoxConfig build() {
            ModBoxConfig newConfig = new ModBoxConfig();
            newConfig.POOL.putAll(POOL);
            return newConfig;
        }

    }
}
