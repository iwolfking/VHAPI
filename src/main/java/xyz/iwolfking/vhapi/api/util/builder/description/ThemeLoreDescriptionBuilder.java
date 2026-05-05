package xyz.iwolfking.vhapi.api.util.builder.description;

import com.google.gson.JsonArray;
import iskallia.vault.config.entry.DescriptionData;
import java.util.ArrayList;
import java.util.List;

public class ThemeLoreDescriptionBuilder {
    private final List<JsonDescription.TextPart> perkParts = new ArrayList<>();
    private JsonDescription.MobCategory horde;
    private JsonDescription.MobCategory assassin;
    private JsonDescription.MobCategory tank;
    private JsonDescription.MobCategory dweller;

    public static ThemeLoreDescriptionBuilder start() {
        return new ThemeLoreDescriptionBuilder();
    }

    public ThemeLoreDescriptionBuilder perk(String text, String color) {
        this.perkParts.add(new JsonDescription.TextPart(text, color));
        return this;
    }

    public ThemeLoreDescriptionBuilder horde(int difficulty, JsonDescription.MobEntry... mobs) {
        this.horde = new JsonDescription.MobCategory(difficulty, mobs);
        return this;
    }

    public ThemeLoreDescriptionBuilder assassin(int difficulty, JsonDescription.MobEntry... mobs) {
        this.assassin = new JsonDescription.MobCategory(difficulty, mobs);
        return this;
    }

    public ThemeLoreDescriptionBuilder tank(int difficulty, JsonDescription.MobEntry... mobs) {
        this.tank = new JsonDescription.MobCategory(difficulty, mobs);
        return this;
    }

    public ThemeLoreDescriptionBuilder dweller(int difficulty, JsonDescription.MobEntry... mobs) {
        this.dweller = new JsonDescription.MobCategory(difficulty, mobs);
        return this;
    }

    public DescriptionData[] build() {
        List<DescriptionData> dataList = new ArrayList<>();

        JsonArray perkArray = new JsonArray();
        perkArray.add(JsonDescription.text("Perk: ", "gray"));
        for (JsonDescription.TextPart part : perkParts) {
            perkArray.add(JsonDescription.text(part.text(), part.color()));
        }
        perkArray.add(JsonDescription.text("\n"));
        dataList.add(JsonDescription.createSection(perkArray));

        JsonArray mobArray = new JsonArray();
        addCategory(mobArray, "Hordes", horde);
        addCategory(mobArray, "Assassins", assassin);
        addCategory(mobArray, "Tanks", tank);
        addCategory(mobArray, "Dwellers", dweller);
        dataList.add(JsonDescription.createSection(mobArray));

        return dataList.toArray(new DescriptionData[0]);
    }

    private void addCategory(JsonArray array, String title, JsonDescription.MobCategory cat) {
        if (cat == null) return;

        array.add(JsonDescription.text(title, "gray"));
        array.add(JsonDescription.text(" | ", "$darkgray"));
        array.add(JsonDescription.text("░".repeat(Math.max(0, Math.min(4, cat.difficulty()))), "gray"));
        array.add(JsonDescription.text("░".repeat(Math.max(0, 4 - cat.difficulty())) + (cat.mobs().length > 0 ? "\n" : ""), "$darkgray"));

        for (JsonDescription.MobEntry mob : cat.mobs()) {
            array.add(JsonDescription.text(" " + mob.name() + " ", "white"));
            array.add(JsonDescription.text("\uD83D\uDDE1", "$orange"));
            array.add(JsonDescription.text(" " + mob.atk(), "white"));
            array.add(JsonDescription.text(" | ", "gray"));
            array.add(JsonDescription.text("❤", "red"));
            array.add(JsonDescription.text(" " + mob.hp(), "white"));
            array.add(JsonDescription.text(" | ", "gray"));
            array.add(JsonDescription.text("\u265D", "$speed"));
            array.add(JsonDescription.text(" " + mob.spd(), "white"));
            array.add(JsonDescription.text(" | ", "gray"));
            array.add(JsonDescription.text(mob.icons() + "\n", "gray"));
        }
    }

    public static JsonDescription.MobEntry mob(String name, int atk, int hp, int spd, String icons) {
        return new JsonDescription.MobEntry(name, atk, hp, spd, icons);
    }
}