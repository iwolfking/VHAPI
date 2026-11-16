package xyz.iwolfking.vhapi.api.loaders.gear.lib;

import com.google.gson.annotations.Expose;
import iskallia.vault.config.Config;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Gear roll types added to the_vault's gear roll type config, in the same shape as that file. Rarities are kept as
 * names so that rarities added to {@code VaultGearRarity} at runtime resolve when the roll types are applied.
 */
public class CustomGearRollTypeConfig extends Config {

    @Expose
    public Map<String, RollTypeEntry> rolls = new LinkedHashMap<>();

    @Override
    public String getName() {
        return "custom_gear_roll_type";
    }

    @Override
    protected void reset() {

    }

    public static class RollTypeEntry {
        @Expose
        public List<RarityWeight> pool = new ArrayList<>();
        @Expose
        public int color;
        @Expose
        public int originalColor;
    }

    public static class RarityWeight {
        @Expose
        public String value;
        @Expose
        public int weight;

        public RarityWeight(String value, int weight) {
            this.value = value;
            this.weight = weight;
        }
    }
}
