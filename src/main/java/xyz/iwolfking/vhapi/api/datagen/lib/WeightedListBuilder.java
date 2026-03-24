package xyz.iwolfking.vhapi.api.datagen.lib;


import iskallia.vault.core.util.WeightedList;

public class WeightedListBuilder<T> {
    WeightedList<T> list = new WeightedList<>();

    public WeightedListBuilder<T> add(T entry, int weight) {
        list.add(entry, weight);
        return this;
    }

    public WeightedList<T> build() {
        return list;
    }
}
