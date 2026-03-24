package xyz.iwolfking.vhapi.api.util.builder.description;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class JsonDescription {

    public static JsonObject empty() {
        JsonObject desc = new JsonObject();
        desc.addProperty("text", "");
        desc.addProperty("color", "$text");
        return desc;
    }

    public static JsonObject simple(String text, String color) {
        JsonObject desc = new JsonObject();
        desc.addProperty("text", text);
        desc.addProperty("color", color);
        return desc;
    }

    public static JsonObject simple(String text) {
        return simple(text, "$text");
    }

    public static JsonArray perk(String perkDescription) {
        JsonArray array = new JsonArray();
        array.add(simple("Perk: ", "gray"));
        array.add(simple(perkDescription, "white"));
        return array;
    }

    public static JsonArray mobs(String mobType, MobEntry... mobEntries) {
        JsonArray array = new JsonArray();
        array.add(simple(mobType + ": ", "gray"));
        array.add(simple(" | ", "$darkgray"));
        array.add(simple("░░░", "gray"));
        array.add(simple("░\n", "$darkgray"));
        for(MobEntry entry : mobEntries) {
            array.add(simple(entry.name, "white"));
            array.add(simple("\uD83D\uDDE1", "$orange"));
            array.add(simple(" " + entry.speedLevel, "white"));
            array.add(simple(" | ", "gray"));
            array.add(simple("❤", "red"));
            array.add(simple(" " + entry.defenseLevel, "white"));
            array.add(simple(" | ", "gray"));
            array.add(simple("♝", "$speed"));
            array.add(simple(" " + entry.attackLevel, "white"));
            array.add(simple(" | ", "gray"));
            array.add(simple("⚔ ☣", "gray"));
            array.add(simple("\n", "gray"));
        }
        return array;
    }

    public static JsonArray dwellers(int dwellerCount) {
        JsonArray array = new JsonArray();
        array.add(simple("Dwellers:", "gray"));
        array.add(simple(" | ", "$darkgray"));
        array.add(simple("░", "gray"));
        StringBuilder builder = new StringBuilder();
        builder.append("░".repeat(Math.max(0, dwellerCount)));
        array.add(simple(builder.toString(), "$darkgray"));
        return array;
    }

    public record MobEntry(String name, int attackLevel, int defenseLevel, int speedLevel) {
    }

}
