package xyz.iwolfking.vhapi.api.datagen.lib;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ListBuilder<T, B extends IGenericBuilder<T>> {
    List<T> entries = new ArrayList<>();
    private final Supplier<B> builderFactory;

    public ListBuilder(Supplier<B> builderFactory) {
        this.builderFactory = builderFactory;
    }

    public ListBuilder<T, B> add(Consumer<B> builderConsumer) {
        B builder = builderFactory.get();
        builderConsumer.accept(builder);
        entries.add(builder.build());
        return this;
    }

    public List<T> build() {
        return entries;
    }
}
