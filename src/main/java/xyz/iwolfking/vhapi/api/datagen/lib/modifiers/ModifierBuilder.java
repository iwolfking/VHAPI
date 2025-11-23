package xyz.iwolfking.vhapi.api.datagen.lib.modifiers;

import java.util.*;

public class ModifierBuilder {

    private final Map<String, Map<String, ModifierDefinition>> modifiers = new LinkedHashMap<>();

    public ModifierBuilder type(String typeKey, TypeConsumer consumer) {
        TypeBuilder tb = new TypeBuilder();
        consumer.accept(tb);
        modifiers.put(typeKey, tb.entries);
        return this;
    }

    public ModifierFile build() {
        return new ModifierFile(modifiers);
    }

    // -----------------------------------------------------
    public static class TypeBuilder {
        private final Map<String, ModifierDefinition> entries = new LinkedHashMap<>();

        public TypeBuilder modifier(String modifierKey, ModifierConsumer consumer) {
            ModifierEntryBuilder mb = new ModifierEntryBuilder();
            consumer.accept(mb);
            entries.put(modifierKey, mb.build());
            return this;
        }
    }

    // -----------------------------------------------------
    public static class ModifierEntryBuilder {
        private final Map<String, Object> properties = new LinkedHashMap<>();
        private ModifierDisplay display;

        public ModifierEntryBuilder property(String key, Object value) {
            properties.put(key, value);
            return this;
        }

        public ModifierEntryBuilder display(String name, String color,
                                            String description, String formatted, String icon) {
            this.display = new ModifierDisplay(name, color, description, formatted, icon);
            return this;
        }

        public ModifierDefinition build() {
            return new ModifierDefinition(properties, display);
        }
    }

    @FunctionalInterface public interface TypeConsumer {
        void accept(TypeBuilder b);
    }

    @FunctionalInterface public interface ModifierConsumer {
        void accept(ModifierEntryBuilder b);
    }
}

