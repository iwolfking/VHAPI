package xyz.iwolfking.vhapi.api.datagen.lib.gear;

import com.google.gson.JsonObject;
import iskallia.vault.config.UniqueCodexConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import xyz.iwolfking.vhapi.api.util.builder.description.JsonDescription;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UniqueGearBuilder {

    private final String name;
    private final Item item;

    private final List<UniqueGearEntry.WeightedModel> models = new ArrayList<>();

    private int weight = 1;

    private final Map<String, List<String>> identifiers = new LinkedHashMap<>();
    private final List<String> modifierTags = new ArrayList<>();

    private String dropLocation = "TBD";
    private String modelType = "SWORD";
    private final List<JsonObject> descriptionData = new ArrayList<>();
    private UniqueCodexConfig.IntroductionPage.SlotType slotType =
            UniqueCodexConfig.IntroductionPage.SlotType.SWORD;
    private boolean uncraftable = false;

    public UniqueGearBuilder(String name, Item item) {
        this.name = name;
        this.item = item;
        identifiers.put("BASE_ATTRIBUTE", new ArrayList<>());
        identifiers.put("IMPLICIT", new ArrayList<>());
        identifiers.put("PREFIX", new ArrayList<>());
        identifiers.put("SUFFIX", new ArrayList<>());
    }


    public UniqueGearBuilder model(String modelPath) {
        return model(modelPath, 1);
    }



    public UniqueGearBuilder model(String modelPath, int modelWeight) {
        ResourceLocation rl = ResourceLocation.tryParse(modelPath);
        return model(rl, modelWeight);
    }

    public UniqueGearBuilder model(ResourceLocation model, int modelWeight) {
        this.models.add(new UniqueGearEntry.WeightedModel(model, modelWeight));
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
        this.descriptionData.add(JsonDescription.simple(description, color));
        return this;
    }

    public UniqueGearBuilder modelType(String modelType) {
        this.modelType = modelType;
        return this;
    }

    public UniqueGearBuilder uncraftable() {
        this.uncraftable = true;
        return this;
    }


    public UniqueGearEntry build() {
        return new UniqueGearEntry(
                name,
                item,
                models,
                identifiers,
                modifierTags,
                weight,
                modelType,
                slotType,
                dropLocation,
                descriptionData,
                uncraftable
        );
    }
}
