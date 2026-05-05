package xyz.iwolfking.vhapi.api.util.builder.description;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import iskallia.vault.config.entry.DescriptionData;

public class JsonDescription {

    public static JsonObject text(String text, String color) {
        JsonObject obj = new JsonObject();
        obj.addProperty("text", text);
        obj.addProperty("color", color);
        return obj;
    }

    public static JsonObject text(String text) {
        JsonObject obj = new JsonObject();
        obj.addProperty("text", text);
        return obj;
    }

    public static JsonObject simple(String text) {
        return text(text);
    }

    public static JsonObject simple(String text, String color) {
        return text(text, color);
    }

    public static DescriptionData createSection(JsonArray textElements) {
        JsonObject section = new JsonObject();
        section.add("description", textElements);
        return new DescriptionData(section);
    }

    // Helper records
    public record TextPart(String text, String color) {}
    public record MobCategory(int difficulty, MobEntry... mobs) {}
    public record MobEntry(String name, int atk, int hp, int spd, String icons) {}
}