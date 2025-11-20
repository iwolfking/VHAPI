package xyz.iwolfking.vhapi.api.lib.core.datagen.lib.gear;

import com.google.gson.JsonObject;
import iskallia.vault.config.UniqueCodexConfig;
import xyz.iwolfking.vhapi.api.util.builder.theme_lore.JsonDescription;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UniqueGearBuilder {

    private final String name;
    private String model;
    private int weight = 1;

    private final Map<String, List<String>> identifiers = new LinkedHashMap<>();
    private final List<String> modifierTags = new ArrayList<>();

    private String dropLocation = "TBD";
    private String modelType = "SWORD";
    private final List<JsonObject> descriptionData = new ArrayList<>();
    private UniqueCodexConfig.IntroductionPage.SlotType slotType = UniqueCodexConfig.IntroductionPage.SlotType.SWORD;

    public UniqueGearBuilder(String name) {
        this.name = name;
        identifiers.put("BASE_ATTRIBUTE", new ArrayList<>());
        identifiers.put("IMPLICIT", new ArrayList<>());
        identifiers.put("PREFIX", new ArrayList<>());
        identifiers.put("SUFFIX", new ArrayList<>());
    }

    public UniqueGearBuilder model(String model) {
        this.model = model;
        return this;
    }

    public UniqueGearBuilder weight(int weight) {
        this.weight = weight;
        return this;
    }

    public UniqueGearBuilder base(String... ids) {
        identifiers.get("BASE_ATTRIBUTE").addAll(List.of(ids));
        return this;
    }

    public UniqueGearBuilder implicit(String... ids) {
        identifiers.get("IMPLICIT").addAll(List.of(ids));
        return this;
    }

    public UniqueGearBuilder prefix(String... ids) {
        identifiers.get("PREFIX").addAll(List.of(ids));
        return this;
    }

    public UniqueGearBuilder suffix(String... ids) {
        identifiers.get("SUFFIX").addAll(List.of(ids));
        return this;
    }

    public UniqueGearBuilder tag(String... tags) {
        modifierTags.addAll(List.of(tags));
        return this;
    }


    public UniqueGearBuilder dropLocation(String dropLocation) {
        this.dropLocation = dropLocation;
        return this;
    }

    public UniqueGearBuilder slotType(UniqueCodexConfig.IntroductionPage.SlotType type) {
        this.slotType = type;
        return this;
    }

    public UniqueGearBuilder description(String description, String color) {
        this.descriptionData.add(JsonDescription.simpleDescription(description, color));
        return this;
    }

    public UniqueGearBuilder modelType(String modelType) {
        this.modelType = modelType;
        return this;
    }


    public UniqueGearEntry build() {
        return new UniqueGearEntry(name, model, identifiers, modifierTags, weight, modelType, slotType, dropLocation, descriptionData);
    }
}
