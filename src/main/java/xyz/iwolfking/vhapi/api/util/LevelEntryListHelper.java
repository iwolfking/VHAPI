package xyz.iwolfking.vhapi.api.util;

import iskallia.vault.config.entry.LevelEntryList;
import iskallia.vault.core.util.WeightedList;

import java.util.Map;
import java.util.function.Function;

public final class LevelEntryListHelper {

    private LevelEntryListHelper() {}

    public static <K, V extends LevelEntryList.ILevelEntry> void mergeMap(
            Map<K, LevelEntryList<V>> source,
            Map<K, LevelEntryList<V>> target,
            Function<V, Integer> getLevel,
            BiMergeFunction<V> mergeFunction
    ) {
        for (Map.Entry<K, LevelEntryList<V>> entry : source.entrySet()) {
            K key = entry.getKey();
            LevelEntryList<V> sourceList = entry.getValue();
            LevelEntryList<V> targetList = target.computeIfAbsent(key, k -> new LevelEntryList<>());

            for (V srcEntry : sourceList) {
                int level = getLevel.apply(srcEntry);
                V existing = targetList.stream()
                                       .filter(e -> getLevel.apply(e) == level)
                                       .findFirst()
                                       .orElse(null);

                if (existing != null) {
                    mergeFunction.merge(existing, srcEntry, targetList);
                } else {
                    targetList.add(srcEntry);
                }
            }
        }
    }

    public static <K, V extends LevelEntryList.ILevelEntry, P> void mergeMap(
            Map<K, LevelEntryList<V>> source,
            Map<K, LevelEntryList<V>> target,
            Function<V, Integer> getLevel,
            Function<V, WeightedList<P>> getPool
    ) {
        for (Map.Entry<K, LevelEntryList<V>> entry : source.entrySet()) {
            K key = entry.getKey();
            LevelEntryList<V> sourceList = entry.getValue();
            LevelEntryList<V> targetList = target.computeIfAbsent(key, k -> new LevelEntryList<>());

            for (V srcEntry : sourceList) {
                int level = getLevel.apply(srcEntry);
                V existing = targetList.stream()
                                       .filter(e -> getLevel.apply(e) == level)
                                       .findFirst()
                                       .orElse(null);

                if (existing != null) {
                    WeightedList<P> targetPool = getPool.apply(existing);
                    WeightedList<P> sourcePool = getPool.apply(srcEntry);
                    targetPool.putAll(sourcePool);
                } else {
                    targetList.add(srcEntry);
                }
            }
        }
    }

    @FunctionalInterface
    public interface BiMergeFunction<V extends LevelEntryList.ILevelEntry> {
        void merge(V targetExisting, V sourceEntry, LevelEntryList<V> levelEntryList);
    }

    public static <V extends LevelEntryList.ILevelEntry> void mergeList(
            LevelEntryList<V> source,
            LevelEntryList<V> target,
            Function<V, Integer> getLevel,
            BiMergeFunction<V> mergeFunction
    ) {
        for (V srcEntry : source) {
            int level = getLevel.apply(srcEntry);
            V existing = target.stream()
                               .filter(e -> getLevel.apply(e) == level)
                               .findFirst()
                               .orElse(null);

            if (existing != null) {
                mergeFunction.merge(existing, srcEntry, target);
            } else {
                target.add(srcEntry);
            }
        }
    }

    public static <V extends LevelEntryList.ILevelEntry, P> void mergeList(
            LevelEntryList<V> source,
            LevelEntryList<V> target,
            Function<V, Integer> getLevel,
            Function<V, WeightedList<P>> getPool
    ) {
        for (V srcEntry : source) {
            int level = getLevel.apply(srcEntry);
            V existing = target.stream()
                               .filter(e -> getLevel.apply(e) == level)
                               .findFirst()
                               .orElse(null);

            if (existing != null) {
                WeightedList<P> targetPool = getPool.apply(existing);
                WeightedList<P> sourcePool = getPool.apply(srcEntry);
                targetPool.putAll(sourcePool);
            } else {
                target.add(srcEntry);
            }
        }
    }
}
