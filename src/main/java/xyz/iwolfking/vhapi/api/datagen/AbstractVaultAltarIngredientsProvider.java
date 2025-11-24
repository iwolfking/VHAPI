package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.altar.VaultAltarIngredientsConfig;
import iskallia.vault.config.altar.entry.AltarIngredientEntry;
import iskallia.vault.config.entry.LevelEntryMap;
import iskallia.vault.util.data.WeightedList;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.ItemStack;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class AbstractVaultAltarIngredientsProvider extends AbstractVaultConfigDataProvider<AbstractVaultAltarIngredientsProvider.Builder> {
    protected AbstractVaultAltarIngredientsProvider(DataGenerator generator, String modid) {
        super(generator, modid, "vaultar", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Vault Altar Ingredients Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<VaultAltarIngredientsConfig> {
        public LevelEntryMap<Map<String, WeightedList<AltarIngredientEntry>>> LEVELS = new LevelEntryMap<>();

        public Builder() {
            super(VaultAltarIngredientsConfig::new);
        }

        public void addIngredients(List<Integer> levels, String category, Consumer<AltarIngredientsBuilder> entriesConsumer) {
            AltarIngredientsBuilder builder = new AltarIngredientsBuilder();
            entriesConsumer.accept(builder);
            levels.forEach(level -> {
                LEVELS.put(level, Map.of(category, builder.build()));
            });
        }

        @Override
        protected void configureConfig(VaultAltarIngredientsConfig config) {
            config.LEVELS.putAll(LEVELS);
        }

        public static class AltarIngredientsBuilder {
            public WeightedList<AltarIngredientEntry> ingredients = new WeightedList<>();

            public AltarIngredientsBuilder add(Consumer<List<ItemStack>> stacksConsumer, int min, int max, double scale, int weight) {
                List<ItemStack> stacks = new ArrayList<>();
                stacksConsumer.accept(stacks);
                ingredients.add(new AltarIngredientEntry(stacks, min, max, scale), weight);
                return this;
            }

            public WeightedList<AltarIngredientEntry> build() {
                return ingredients;
            }
        }

    }
}
