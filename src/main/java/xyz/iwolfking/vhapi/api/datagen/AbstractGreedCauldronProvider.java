package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.greed.GreedCauldronConfig;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.GreedCauldronConfigAccessor;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractGreedCauldronProvider extends AbstractVaultConfigDataProvider<AbstractGreedCauldronProvider.Builder> {
    protected AbstractGreedCauldronProvider(DataGenerator generator, String modid) {
        super(generator, modid, "greed/cauldron", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Greed Cauldron Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<GreedCauldronConfig> {
        List<GreedCauldronConfig.DemandEntry> demands = new LinkedList<>();

        public Builder() {
            super(GreedCauldronConfig::new);
        }

        public Builder add(String itemName, int minAmount, int maxAmount, Integer coinOutput) {
            new GreedCauldronConfig.DemandEntry(itemName, minAmount, maxAmount, coinOutput);
            return this;
        }

        public Builder add(ResourceLocation itemId, int minAmount, int maxAmount, Integer coinOutput) {
            return add(itemId.toString(), minAmount, maxAmount, coinOutput);
        }

        public Builder add(ItemLike item, int minAmount, int maxAmount, Integer coinOutput) {
            return add(item.asItem().getRegistryName().toString(), minAmount, maxAmount, coinOutput);
        }

        @Override
        protected void configureConfig(GreedCauldronConfig config) {
            ((GreedCauldronConfigAccessor)config).getDemands().addAll(demands);
        }
    }
}
