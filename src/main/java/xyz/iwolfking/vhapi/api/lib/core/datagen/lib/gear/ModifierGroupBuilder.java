package xyz.iwolfking.vhapi.api.lib.core.datagen.lib.gear;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ModifierGroupBuilder {

    private final Map<String, List<ModifierEntry>> groupMap = new LinkedHashMap<>();

    public ModifierGroupBuilder category(String groupName, Consumer<CategoryBuilder> consumer) {
        CategoryBuilder b = new CategoryBuilder();
        consumer.accept(b);
        groupMap.put(groupName, b.entries);
        return this;
    }

    public ModifierGroupFile build() {
        return new ModifierGroupFile(groupMap);
    }

    public static class CategoryBuilder {
        private final List<ModifierEntry> entries = new ArrayList<>();

        public CategoryBuilder entry(String attribute, String group, String id, Consumer<EntryBuilder> consumer) {
            EntryBuilder eb = new EntryBuilder(attribute, group, id);
            consumer.accept(eb);
            entries.add(eb.build());
            return this;
        }
    }

    public static class EntryBuilder {
        private final String attribute;
        private final String group;
        private final String identifier;
        private final List<String> tags = new ArrayList<>();
        private final List<Tier> tiers = new ArrayList<>();

        public EntryBuilder(String attribute, String group, String identifier) {
            this.attribute = attribute;
            this.group = group;
            this.identifier = identifier;
        }

        public EntryBuilder tag(String tag) {
            tags.add(tag);
            return this;
        }

        public EntryBuilder tier(int min, int max, int weight, Object value) {
            tiers.add(new Tier(min, max, weight, value));
            return this;
        }

        public ModifierEntry build() {
            return new ModifierEntry(attribute, group, identifier, tags, tiers);
        }
    }
}

