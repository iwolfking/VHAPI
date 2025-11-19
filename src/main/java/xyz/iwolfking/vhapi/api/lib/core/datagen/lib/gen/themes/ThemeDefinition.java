package xyz.iwolfking.vhapi.api.lib.core.datagen.lib.gen.themes;

import com.google.gson.JsonObject;
import iskallia.vault.config.ThemeAugmentLoreConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ThemeDefinition {

    public String type;
    public String starts;
    public String rooms;
    public String tunnels;

    public float ambientLight;
    public int fogColor;
    public int grassColor;
    public int foliageColor;
    public int waterColor;
    public int waterFogColor;

    public String particle;
    public float particleProbability;

    public int themeColor;

    public Map<String, Set<Integer>> levelEntries = new HashMap<>();
    public int themeWeight = 0;

    public String themeGroup;
    public ThemeAugmentLoreConfig.AugmentLore themeLore;

    public JsonObject toJson() {
        JsonObject root = new JsonObject();

        root.addProperty("type", type);
        root.addProperty("starts", starts);
        root.addProperty("rooms", rooms);
        root.addProperty("tunnels", tunnels);

        root.addProperty("ambient_light", ambientLight);
        root.addProperty("fog_color", fogColor);
        root.addProperty("grass_color", grassColor);
        root.addProperty("foliage_color", foliageColor);
        root.addProperty("water_color", waterColor);
        root.addProperty("water_fog_color", waterFogColor);

        root.addProperty("particle", particle);
        root.addProperty("particle_probability", particleProbability);

        return root;
    }
}
