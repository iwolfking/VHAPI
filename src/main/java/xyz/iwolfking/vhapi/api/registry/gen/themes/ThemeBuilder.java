package xyz.iwolfking.vhapi.api.registry.gen.themes;

import iskallia.vault.config.ThemeAugmentLoreConfig;

import java.util.HashSet;
import java.util.Set;

public class ThemeBuilder {

    private final ThemeDefinition def = new ThemeDefinition();

    public ThemeBuilder type(String type) {
        def.type = type;
        return this;
    }

    public ThemeBuilder starts(String value) {
        def.starts = value;
        return this;
    }

    public ThemeBuilder rooms(String value) {
        def.rooms = value;
        return this;
    }

    public ThemeBuilder tunnels(String value) {
        def.tunnels = value;
        return this;
    }

    public ThemeBuilder ambientLight(float f) {
        def.ambientLight = f;
        return this;
    }

    public ThemeBuilder fogColor(int c) {
        def.fogColor = c;
        return this;
    }

    public ThemeBuilder grassColor(int c) {
        def.grassColor = c;
        return this;
    }

    public ThemeBuilder foliageColor(int c) {
        def.foliageColor = c;
        return this;
    }

    public ThemeBuilder waterColor(int c) {
        def.waterColor = c;
        return this;
    }

    public ThemeBuilder waterFogColor(int c) {
        def.waterFogColor = c;
        return this;
    }

    public ThemeBuilder particle(String id) {
        def.particle = id;
        return this;
    }

    public ThemeBuilder particleProbability(float f) {
        def.particleProbability = f;
        return this;
    }

    public ThemeBuilder themeColor(int color) {
        def.themeColor = color;
        return this;
    }

    public ThemeBuilder levelEntry(String pool, int level) {
        if(def.levelEntries.containsKey(pool)) {
            def.levelEntries.get(pool).add(level);
        }
        else {
            Set<Integer> levelEntries = new HashSet<>();
            levelEntries.add(level);
            def.levelEntries.put(pool, levelEntries);
        }
        return this;
    }

    public ThemeBuilder themeWeight(int weight) {
        def.themeWeight = weight;
        return this;
    }

    public ThemeBuilder themeGroup(String groupName) {
        def.themeGroup = groupName;
        return this;
    }

    public ThemeBuilder themeLore(ThemeAugmentLoreConfig.AugmentLore augmentLore) {
        def.themeLore = augmentLore;
        return this;
    }

    public ThemeDefinition build() {
        return def;
    }
}
