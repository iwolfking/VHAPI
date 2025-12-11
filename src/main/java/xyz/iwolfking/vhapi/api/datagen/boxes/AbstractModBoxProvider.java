package xyz.iwolfking.vhapi.api.datagen.boxes;

import iskallia.vault.config.ModBoxConfig;
import iskallia.vault.config.entry.vending.ProductEntry;
import iskallia.vault.util.data.WeightedList;
import net.minecraft.data.DataGenerator;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import xyz.iwolfking.vhapi.api.datagen.AbstractVaultConfigDataProvider;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.api.datagen.lib.loot.LootableConfigBuilder;
import xyz.iwolfking.vhapi.api.datagen.lib.loot.ProductEntryListBuilder;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractModBoxProvider extends AbstractVaultConfigDataProvider<AbstractModBoxProvider.Builder> {
    protected AbstractModBoxProvider(DataGenerator generator, String modid) {
        super(generator, modid, "loot_box/mod_box", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Mod Box Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<ModBoxConfig> {
        public Map<String, ModBoxConfig.ModPool> POOL = new HashMap<>();

        public Builder() {
            super(ModBoxConfig::new);
        }

        public Builder addModBox(String researchName, Consumer<ProductEntryListBuilder> poolConsumer, int weight) {
            ProductEntryListBuilder pool = new ProductEntryListBuilder();
            poolConsumer.accept(pool);
            ModBoxConfig.ModPool pool1 = new ModBoxConfig.ModPool();
            pool1.weight = weight;
            pool1.entries.addAll(pool.build());
            POOL.put(researchName, pool1);
            return this;
        }
        public Builder addModBox(String researchName, Consumer<ProductEntryListBuilder> poolConsumer) {
            return addModBox(researchName, poolConsumer, 1);
        }


        @Override
        protected void configureConfig(ModBoxConfig config) {
            config.POOL.putAll(POOL);
        }

    }
}
