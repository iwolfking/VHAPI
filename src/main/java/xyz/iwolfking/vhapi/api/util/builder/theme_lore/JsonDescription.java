package xyz.iwolfking.vhapi.api.util.builder.theme_lore;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class JsonDescription {

    public static JsonObject simpleDescription(String text, String color) {
        JsonObject desc = new JsonObject();
        desc.addProperty("text", text);
        desc.addProperty("color", color);
        return desc;
    }

    public static JsonArray perkDescription(String perkDescription) {
        JsonArray array = new JsonArray();
        array.add(simpleDescription("Perk: ", "gray"));
        array.add(simpleDescription(perkDescription, "white"));
        return array;
    }

    public static JsonArray mobsDescription(String mobType, MobEntry... hordeEntries) {
        JsonArray array = new JsonArray();
        array.add(simpleDescription(mobType + ": ", "gray"));
        array.add(simpleDescription(" | ", "$darkgray"));
        array.add(simpleDescription("░░░", "gray"));
        array.add(simpleDescription("░\n", "$darkgray"));
        for(MobEntry entry : hordeEntries) {
            array.add(simpleDescription(entry.name, "white"));
            array.add(simpleDescription("\uD83D\uDDE1", "$orange"));
            array.add(simpleDescription(" " + entry.speedLevel, "white"));
            array.add(simpleDescription(" | ", "gray"));
            array.add(simpleDescription("❤", "red"));
            array.add(simpleDescription(" " + entry.defenseLevel, "white"));
            array.add(simpleDescription(" | ", "gray"));
            array.add(simpleDescription("♝", "$speed"));
            array.add(simpleDescription(" " + entry.attackLevel, "white"));
            array.add(simpleDescription(" | ", "gray"));
            array.add(simpleDescription("⚔ ☣", "gray"));
            array.add(simpleDescription("\n", "gray"));
        }
        return array;
    }

    public static JsonArray dwellersDescription(int dwellerCount) {
        JsonArray array = new JsonArray();
        array.add(simpleDescription("Dwellers:", "gray"));
        array.add(simpleDescription(" | ", "$darkgray"));
        array.add(simpleDescription("░", "gray"));
        StringBuilder builder = new StringBuilder();
        builder.append("░".repeat(Math.max(0, dwellerCount)));
        array.add(simpleDescription(builder.toString(), "$darkgray"));
        return array;
    }

    public record MobEntry(String name, int attackLevel, int defenseLevel, int speedLevel) {
    }

}
