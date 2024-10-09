package xyz.iwolfking.vaultcrackerlib.api.patching.configs.lib;


import iskallia.vault.config.altar.entry.AltarIngredientEntry;
import iskallia.vault.config.entry.LevelEntryMap;
import iskallia.vault.util.data.WeightedList;
import xyz.iwolfking.vaultcrackerlib.api.patching.configs.lib.BasicPatcher;

import java.util.HashMap;
import java.util.Map;
@Deprecated
public class LeveledWeightedListInjector<T, S> extends BasicPatcher {
    public Map<Integer, WeightedList<T>> LEVEL_MAP = new HashMap<>();

    private final S poolKey;

    public LeveledWeightedListInjector(S poolName) {
        this.poolKey = poolName;
    }

    public WeightedList<T> getForLevel(int level) {
        return LEVEL_MAP.containsKey(level) ? LEVEL_MAP.get(level) : new WeightedList<T>();
    }

    public void addEntries(int level, WeightedList<T> entries) {
        if(LEVEL_MAP.containsKey(level)) {
            LEVEL_MAP.get(level).addAll(entries);
            return;
        }
        LEVEL_MAP.put(level, entries);
    }

    public void addEntry(int level, T entry, int weight) {
        if(LEVEL_MAP.containsKey(level)) {
            LEVEL_MAP.get(level).add(entry, weight);
        }
        else {
            WeightedList<T> entries = new WeightedList<>();
            entries.add(entry, weight);
            LEVEL_MAP.put(level, entries);
        }

    }

    public Map<Integer, WeightedList<T>> getLevelMap() {
        return LEVEL_MAP;
    }

    public void doPatches(LevelEntryMap<Map<S, WeightedList<T>>> levelEntryMap) {
        if(!this.isPatched()) {
            for(Map.Entry<Integer, Map<S, WeightedList<T>>> leveledEntries : levelEntryMap.entrySet()) {
                if(this.LEVEL_MAP.containsKey(leveledEntries.getKey())) {
                    leveledEntries.getValue().get(poolKey).addAll(LEVEL_MAP.get(leveledEntries.getKey()));
                }
            }
        }
    }
}
