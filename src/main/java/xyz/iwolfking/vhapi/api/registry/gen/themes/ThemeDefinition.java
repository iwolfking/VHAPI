package xyz.iwolfking.vhapi.api.registry.gen.themes;

import com.google.gson.JsonObject;

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
