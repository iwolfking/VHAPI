package xyz.iwolfking.vhapi.api.lib.core.datagen.lib.gear;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

public record ModifierGroupFile(Map<String, List<ModifierEntry>> modifierGroup) {

    /**
     * Produce the JSON element ready to be written to file:
     * { "modifierGroup": { "CATEGORY": [ { ... }, ... ], ... } }
     */
    public JsonElement toJson() {
        JsonObject root = new JsonObject();
        JsonObject groupObj = new JsonObject();

        for (Map.Entry<String, List<ModifierEntry>> catEntry : modifierGroup.entrySet()) {
            String category = catEntry.getKey();
            List<ModifierEntry> entries = catEntry.getValue();

            JsonArray entryArray = new JsonArray();
            for (ModifierEntry e : entries) {
                JsonObject entryObj = new JsonObject();

                // accessor names below may be getXxx() or attribute() depending on your class; adjust as needed
                entryObj.addProperty("attribute", e.getAttribute());
                entryObj.addProperty("group", e.getGroup());
                entryObj.addProperty("identifier", e.getIdentifier());

                // tags (optional)
                if (e.getTags() != null && !e.getTags().isEmpty()) {
                    JsonArray tagArray = new JsonArray();
                    for (String t : e.getTags()) tagArray.add(t);
                    entryObj.add("tags", tagArray);
                }

                // tiers array
                JsonArray tiersArray = new JsonArray();
                if (e.getTiers() != null) {
                    for (Tier t : e.getTiers()) {
                        JsonObject tObj = new JsonObject();
                        tObj.addProperty("minLevel", t.minLevel());
                        tObj.addProperty("maxLevel", t.maxLevel());
                        tObj.addProperty("weight", t.weight());

                        // Value serialization – simple polymorphic handling:
                        Object val = t.value();
                        if (val instanceof RangeValue) {
                            RangeValue rv = (RangeValue) val;
                            JsonObject v = new JsonObject();
                            v.addProperty("min", rv.min());
                            v.addProperty("max", rv.max());
                            v.addProperty("step", rv.step());
                            tObj.add("value", v);
                        } else if (val instanceof FlagValue) {
                            FlagValue fv = (FlagValue) val;
                            JsonObject v = new JsonObject();
                            v.addProperty("flag", fv.flag());
                            tObj.add("value", v);
                        } else if (val instanceof AbilityValue) {
                            AbilityValue av = (AbilityValue) val;
                            JsonObject v = new JsonObject();
                            v.addProperty("abilityKey", av.abilityKey());
                            v.addProperty("levelChange", av.levelChange());
                            tObj.add("value", v);
                        } else if (val instanceof JsonSerializableValue) {
                            // If you already have a value class that can produce JsonElement, use it:
                            JsonElement vEl = ((JsonSerializableValue) val).toJson();
                            tObj.add("value", vEl);
                        } else {
                            // fallback: try to treat it as a number / boolean / string
                            JsonObject v = new JsonObject();
                            v.addProperty("raw", String.valueOf(val));
                            tObj.add("value", v);
                        }

                        tiersArray.add(tObj);
                    }
                }

                entryObj.add("tiers", tiersArray);
                entryArray.add(entryObj);
            }

            groupObj.add(category, entryArray);
        }

        root.add("modifierGroup", groupObj);
        return root;
    }
}
