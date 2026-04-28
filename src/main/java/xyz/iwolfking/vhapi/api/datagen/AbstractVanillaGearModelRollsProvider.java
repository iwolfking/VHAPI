package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.GearModelRollRaritiesConfig;
import iskallia.vault.gear.VaultGearRarity;
import net.minecraft.data.DataGenerator;
import xyz.iwolfking.vhapi.api.datagen.lib.BasicListBuilder;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.GearModelRollRaritiesAccessor;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractVanillaGearModelRollsProvider extends AbstractVaultConfigDataProvider<AbstractVanillaGearModelRollsProvider.Builder> {
    protected AbstractVanillaGearModelRollsProvider(DataGenerator generator, String modid) {
        super(generator, modid, "gear/model_rolls", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Gear Model Rolls Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<GearModelRollRaritiesConfig> {
        Map<String, List<String>> ARMOR_MODEL_ROLLS = new LinkedHashMap<>();
        Map<String, List<String>> SWORD_MODEL_ROLLS = new LinkedHashMap<>();
        Map<String, List<String>> AXE_MODEL_ROLLS = new LinkedHashMap<>();
        Map<String, List<String>> SHIELD_MODEL_ROLLS = new LinkedHashMap<>();
        Map<String, List<String>> WAND_MODEL_ROLLS = new LinkedHashMap<>();
        Map<String, List<String>> FOCUS_MODEL_ROLLS = new LinkedHashMap<>();
        Map<String, List<String>> MAGNETS_MODEL_ROLLS = new LinkedHashMap<>();

        public Builder() {
            super(GearModelRollRaritiesConfig::new);
        }

        public Builder addForRarity(VaultGearRarity rarity, ModelType type, Consumer<BasicListBuilder<String>> modelsConsumer) {
            BasicListBuilder<String> builder = new BasicListBuilder<>();
            modelsConsumer.accept(builder);
            switch (type) {
                case ARMOR -> ARMOR_MODEL_ROLLS.put(rarity.name(), builder.build());
                case SWORD -> SWORD_MODEL_ROLLS.put(rarity.name(), builder.build());
                case AXE -> AXE_MODEL_ROLLS.put(rarity.name(), builder.build());
                case FOCUS -> FOCUS_MODEL_ROLLS.put(rarity.name(), builder.build());
                case WAND -> WAND_MODEL_ROLLS.put(rarity.name(), builder.build());
                case SHIELD -> SHIELD_MODEL_ROLLS.put(rarity.name(), builder.build());
                case MAGNETS -> MAGNETS_MODEL_ROLLS.put(rarity.name(), builder.build());
            }
            return this;
        }

        @Override
        protected void configureConfig(GearModelRollRaritiesConfig config) {
            ((GearModelRollRaritiesAccessor)config).setArmorModelRolls(ARMOR_MODEL_ROLLS);
            ((GearModelRollRaritiesAccessor)config).setSwordModelRolls(SWORD_MODEL_ROLLS);
            ((GearModelRollRaritiesAccessor)config).setAxeModelRolls(AXE_MODEL_ROLLS);
            ((GearModelRollRaritiesAccessor)config).setShieldModelRolls(SHIELD_MODEL_ROLLS);
            ((GearModelRollRaritiesAccessor)config).setWandModelRolls(WAND_MODEL_ROLLS);
            ((GearModelRollRaritiesAccessor)config).setFocusModelRolls(FOCUS_MODEL_ROLLS);
            ((GearModelRollRaritiesAccessor)config).setMagnetsModelRolls(MAGNETS_MODEL_ROLLS);
        }


    }

    public enum ModelType {
        ARMOR,
        SWORD,
        AXE,
        SHIELD,
        WAND,
        FOCUS,
        MAGNETS
    }
}
