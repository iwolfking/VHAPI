package xyz.iwolfking.vhapi.api.datagen.lib;

import java.util.ArrayList;
import java.util.List;

public class BasicListBuilder<T> {
    List<T> entries = new ArrayList<>();


    public BasicListBuilder<T> add(T entry) {
        entries.add(entry);
        return this;
    }

    public List<T> build() {
        return entries;
    }
}
