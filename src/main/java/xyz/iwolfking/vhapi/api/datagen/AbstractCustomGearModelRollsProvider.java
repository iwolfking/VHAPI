package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.VaultMod;
import iskallia.vault.gear.VaultGearRarity;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.datagen.lib.BasicListBuilder;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.api.loaders.gear.transmog.lib.CustomGearModelRollRaritiesConfig;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractCustomGearModelRollsProvider extends AbstractVaultConfigDataProvider<AbstractCustomGearModelRollsProvider.Builder> {
    protected AbstractCustomGearModelRollsProvider(DataGenerator generator, String modid) {
        super(generator, modid, "gear/custom/model_rolls", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Custom Gear Model Rolls Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<CustomGearModelRollRaritiesConfig> {
        Map<String, List<String>> MODEL_ROLLS = new LinkedHashMap<>();
        ResourceLocation itemId = VaultMod.id("battlestaff");

        public Builder() {
            super(CustomGearModelRollRaritiesConfig::new);
        }

        private Builder add(VaultGearRarity rarity, Consumer<BasicListBuilder<String>> modelsConsumer, Map<String, List<String>> modelMap) {
            BasicListBuilder<String> builder = new BasicListBuilder<>();
            modelsConsumer.accept(builder);
            modelMap.put(rarity.name(), builder.build());
            return this;
        }

        public Builder addForRarity(VaultGearRarity rarity, Consumer<BasicListBuilder<String>> modelsConsumer) {
            return add(rarity, modelsConsumer, MODEL_ROLLS);
        }

        public Builder item(ResourceLocation itemId) {
            this.itemId = itemId;
            return this;
        }

        @Override
        protected void configureConfig(CustomGearModelRollRaritiesConfig config) {
            config.MODEL_ROLLS.putAll(MODEL_ROLLS);
            config.itemRegistryName = itemId;
        }
    }
}
