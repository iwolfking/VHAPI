package xyz.iwolfking.vhapi.api.datagen.lib.gen.themes;

import com.google.gson.JsonObject;
import iskallia.vault.config.ThemeAugmentLoreConfig;
import iskallia.vault.config.entry.DescriptionData;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public ResourceLocation atmosphere;
    public int minLevel = 0;
    public boolean seasonal = false;
    public String perk;
    public FlairDefinition flair;
    public List<ResourceLocation> mobPalettes = new ArrayList<>();

    public ThemeDefinition setGroup(String group) {
        this.themeGroup = group;
        return this;
    }

    public ThemeDefinition setLore(String displayName, String description, int colour) {
        this.themeLore = new ThemeAugmentLoreConfig.AugmentLore(
                displayName,
                new DescriptionData[]{DescriptionData.getDefault(description)},
                colour
        );
        return this;
    }

    public ThemeDefinition setLore(String displayName, DescriptionData[] description, int colour) {
        this.themeLore = new ThemeAugmentLoreConfig.AugmentLore(displayName, description, colour);
        return this;
    }

    public ThemeDefinition setMinLevel(int minLevel) {
        this.minLevel = minLevel;
        return this;
    }

    public ThemeDefinition setSeasonal(boolean seasonal) {
        this.seasonal = seasonal;
        return this;
    }

    public ThemeDefinition setPerk(String perk) {
        this.perk = perk;
        return this;
    }

    public ThemeDefinition setAtmosphere(ResourceLocation atmosphere) {
        this.atmosphere = atmosphere;
        return this;
    }

    public ThemeDefinition addMobPalette(ResourceLocation palette) {
        this.mobPalettes.add(palette);
        return this;
    }

    public ThemeDefinition setFlair(ResourceLocation floor, ResourceLocation wall, ResourceLocation accent, ResourceLocation particle) {
        this.flair = new FlairDefinition(floor, wall, accent, particle);
        return this;
    }

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

    public static record FlairDefinition(
            ResourceLocation floor,
            ResourceLocation wall,
            ResourceLocation accent,
            ResourceLocation particle
    ) {}
}