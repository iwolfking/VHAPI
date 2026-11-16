package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.gear.VaultGearRarity;
import net.minecraft.data.DataGenerator;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.api.loaders.gear.lib.CustomGearRollTypeConfig;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractGearRollTypeProvider extends AbstractVaultConfigDataProvider<AbstractGearRollTypeProvider.Builder> {
    protected AbstractGearRollTypeProvider(DataGenerator generator, String modid) {
        super(generator, modid, "gear/roll_types", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Gear Roll Types Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<CustomGearRollTypeConfig> {
        private final Map<String, CustomGearRollTypeConfig.RollTypeEntry> rolls = new LinkedHashMap<>();

        public Builder() {
            super(CustomGearRollTypeConfig::new);
        }

        /** Adds a roll type drawn in {@code color}, which is also the color it returns to with no color blind mode. */
        public Builder add(String name, int color, Consumer<PoolBuilder> poolConsumer) {
            PoolBuilder poolBuilder = new PoolBuilder();
            poolConsumer.accept(poolBuilder);
            CustomGearRollTypeConfig.RollTypeEntry entry = new CustomGearRollTypeConfig.RollTypeEntry();
            entry.pool = poolBuilder.build(name);
            entry.color = color;
            entry.originalColor = color;
            rolls.put(name, entry);
            return this;
        }

        @Override
        protected void configureConfig(CustomGearRollTypeConfig config) {
            config.rolls.putAll(rolls);
        }
    }

    public static class PoolBuilder {
        private final List<CustomGearRollTypeConfig.RarityWeight> pool = new ArrayList<>();

        /** Adds a rarity under its enum name, so rarities added to {@code VaultGearRarity} at runtime are written too. */
        public PoolBuilder add(VaultGearRarity rarity, int weight) {
            if (weight < 1) {
                throw new IllegalArgumentException("Rarity " + rarity.name() + " needs a weight of at least 1, got " + weight);
            }
            pool.add(new CustomGearRollTypeConfig.RarityWeight(rarity.name(), weight));
            return this;
        }

        private List<CustomGearRollTypeConfig.RarityWeight> build(String rollTypeName) {
            if (pool.isEmpty()) {
                throw new IllegalArgumentException("Gear roll type " + rollTypeName + " has an empty pool");
            }
            return new ArrayList<>(pool);
        }
    }
}
