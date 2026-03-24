package xyz.iwolfking.vhapi.api.datagen.lib.gear;

public record Value(
        // numeric value
        Double min,
        Double max,
        Double step,
        // boolean flag
        Boolean flag,
        // ability fields
        String abilityKey,
        Integer levelChange
) {
    public static Value range(double min, double max, double step) {
        return new Value(min, max, step, null, null, null);
    }

    public static Value flag(boolean v) {
        return new Value(null, null, null, v, null, null);
    }

    public static Value ability(String key, int levelChange) {
        return new Value(null, null, null, null, key, levelChange);
    }
}
