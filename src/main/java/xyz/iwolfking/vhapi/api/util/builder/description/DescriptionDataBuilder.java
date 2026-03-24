package xyz.iwolfking.vhapi.api.util.builder.description;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import iskallia.vault.config.entry.DescriptionData;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DescriptionDataBuilder {
    private final List<DescriptionData> description = new ArrayList<>();

    public DescriptionDataBuilder description(Consumer<JsonArray> jsonElementConsumer) {
        JsonArray element = new JsonArray();
        jsonElementConsumer.accept(element);
        JsonObject wrapper = new JsonObject();
        wrapper.add("description", element);
        description.add(new DescriptionData(wrapper));
        return this;
    }

    public DescriptionDataBuilder description(JsonArray jsonArray) {
        JsonObject wrapper = new JsonObject();
        wrapper.add("description", jsonArray);
        description.add(new DescriptionData(wrapper));
        return this;
    }

    public DescriptionData[] build() {
        return description.toArray(DescriptionData[]::new);
    }
}
