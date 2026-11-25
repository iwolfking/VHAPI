package xyz.iwolfking.vhapi.api.loaders.gear;

import iskallia.vault.config.gear.VaultGearTypeConfig;
import iskallia.vault.gear.VaultGearRarity;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.util.data.WeightedList;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.VHAPI;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.gear.lib.CustomGearRollTypeConfig;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.api.util.vhapi.VHAPILoggerUtils;
import xyz.iwolfking.vhapi.mixin.accessors.VaultGearTypeConfigAccessor;

import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;

public class GearRollTypeConfigLoader extends VaultConfigProcessor<CustomGearRollTypeConfig> {

    public GearRollTypeConfigLoader() {
        super(new CustomGearRollTypeConfig(), "gear/roll_types");
    }

    /**
     * Puts every datapack roll type into the_vault's gear roll type config, replacing a roll type of the same name.
     * A roll type whose pool is empty, names an unknown rarity or has a weight below 1 logs an error and is skipped.
     */
    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        Map<String, VaultGearTypeConfig.RollType> rolls = ((VaultGearTypeConfigAccessor) ModConfigs.VAULT_GEAR_TYPE_CONFIG).getRolls();
        this.CUSTOM_CONFIGS.forEach((id, config) -> {
            if (config.rolls == null) {
                VHAPI.LOGGER.error("Gear roll type config {} has no rolls; it adds nothing", id);
                return;
            }
            config.rolls.forEach((name, entry) -> toRollType(id, name, entry).ifPresent(rollType -> {
                rolls.put(name, rollType);
                VHAPILoggerUtils.debug(String.format("Added gear roll type %s from %s: %s", name, id, describe(rollType.pool)));
            }));
        });
        super.afterConfigsLoad(event);
    }

    private static Optional<VaultGearTypeConfig.RollType> toRollType(ResourceLocation id, String name, CustomGearRollTypeConfig.RollTypeEntry entry) {
        if (entry == null || entry.pool == null || entry.pool.isEmpty()) {
            VHAPI.LOGGER.error("Gear roll type {} in {} has no pool; it is skipped", name, id);
            return Optional.empty();
        }
        WeightedList<VaultGearRarity> pool = new WeightedList<>();
        for (CustomGearRollTypeConfig.RarityWeight rarityWeight : entry.pool) {
            String rarityName = rarityWeight == null ? null : rarityWeight.value;
            Optional<VaultGearRarity> rarity = rarityByName(rarityName);
            if (rarity.isEmpty()) {
                VHAPI.LOGGER.error("Gear roll type {} in {} names unknown rarity '{}'; it is skipped", name, id, rarityName);
                return Optional.empty();
            }
            if (rarityWeight.weight < 1) {
                VHAPI.LOGGER.error("Gear roll type {} in {} gives rarity {} weight {}; it is skipped", name, id, rarityName, rarityWeight.weight);
                return Optional.empty();
            }
            pool.add(rarity.get(), rarityWeight.weight);
        }
        VaultGearTypeConfig.RollType rollType = new VaultGearTypeConfig.RollType(pool);
        rollType.color = entry.color;
        rollType.originalColor = entry.originalColor;
        return Optional.of(rollType);
    }

    private static String describe(WeightedList<VaultGearRarity> pool) {
        StringJoiner joiner = new StringJoiner(", ");
        pool.forEach((rarity, weight) -> joiner.add(rarity.name() + "=" + weight));
        return joiner.toString();
    }

    private static Optional<VaultGearRarity> rarityByName(String name) {
        for (VaultGearRarity rarity : VaultGearRarity.values()) {
            if (rarity.name().equals(name)) {
                return Optional.of(rarity);
            }
        }
        return Optional.empty();
    }
}
