package xyz.iwolfking.vhapi.api.lib.core.datagen.gen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import iskallia.vault.VaultMod;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.lib.core.datagen.lib.gen.palette.PaletteBuilder;
import xyz.iwolfking.vhapi.api.lib.core.datagen.lib.gen.palette.PaletteDefinition;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;

public abstract class AbstractPaletteProvider implements DataProvider {

    protected final DataGenerator generator;
    protected final String modid;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private final Map<ResourceLocation, PaletteDefinition> palettes = new LinkedHashMap<>();
    private final List<JsonObject> paletteRegistryEntries = new ArrayList<>();

    public AbstractPaletteProvider(DataGenerator generator, String modid) {
        this.generator = generator;
        this.modid = modid;
    }

    protected abstract void registerPalettes();

    protected <T extends PaletteBuilder> void add(ResourceLocation id, T builder, Consumer<T> consumer) {
        consumer.accept(builder);
        palettes.put(id, builder.build());
    }


    @Override
    public void run(HashCache cache) throws IOException {
        palettes.clear();
        registerPalettes();

        Path output = generator.getOutputFolder();

        for (var entry : palettes.entrySet()) {
            ResourceLocation id = entry.getKey();
            PaletteDefinition def = entry.getValue();

            Path palettePath = output.resolve(
                    "data/" + id.getNamespace() + "/vault_configs/gen/palettes/" + id.getPath() + ".json"
            );
            DataProvider.save(GSON, cache, def.toJson(), palettePath);

            JsonObject item = new JsonObject();
            item.addProperty("id", id.toString());
            item.addProperty("name", formatReadableName(id));
            item.addProperty("1.0", id.toString());

            paletteRegistryEntries.add(item);
        }


        Path keyPath = output.resolve(
                "data/" + modid + "/vault_configs/palettes/palettes.json"
        );

        JsonObject keyFile = new JsonObject();
        JsonArray keys = new JsonArray();

        for(JsonObject entry : paletteRegistryEntries) {
            keys.add(entry);
        }

        keyFile.add("keys", keys);

        DataProvider.save(GSON, cache, keyFile, keyPath);

    }

    @Override
    public String getName() {
        return modid + " Palette Provider";
    }

    private static String formatReadableName(ResourceLocation rl) {
        String path = rl.getPath().replace('/', '_');

        String[] parts = path.split("_");
        StringBuilder sb = new StringBuilder();

        for (String p : parts) {
            if (p.isEmpty()) continue;
            sb.append(Character.toUpperCase(p.charAt(0)))
                    .append(p.substring(1))
                    .append(" ");
        }

        return sb.toString().trim();
    }

    public static class ThemePaletteBuilder extends PaletteBuilder {

        Map<ThemeBlockType, Map<ResourceLocation, Integer>> themeBlocks = new HashMap<>();

        public ThemePaletteBuilder placeholder(ResourceLocation placeholderId) {
            this.reference(placeholderId);
            return this;
        }

        public ThemePaletteBuilder placeholder(Placeholder placeholder) {
            return placeholder(placeholder.getId());
        }

        public ThemePaletteBuilder replace(ThemeBlockType type, ResourceLocation blockId, int weight) {
            if(this.themeBlocks.containsKey(type)) {
                themeBlocks.get(type).put(blockId, weight);
            }
            else {
                Map<ResourceLocation, Integer> blockMap = new HashMap<>();
                blockMap.put(blockId, weight);
                this.themeBlocks.put(type, blockMap);
            }
            return this;
        }

        public ThemePaletteBuilder replace(ThemeBlockType type, Consumer<Map<ResourceLocation, Integer>> replacements) {
            Map<ResourceLocation, Integer> replacementBlocks = new HashMap<>();
            replacements.accept(replacementBlocks);
            if(this.themeBlocks.containsKey(type)) {
                replacementBlocks.forEach((res, weight) -> themeBlocks.get(type).put(res, weight));
            }
            else {
                this.themeBlocks.put(type, replacementBlocks);
            }

            return this;
        }

        public ThemePaletteBuilder themeCategory(String themeName, String themeCategory) {
            this.templateStackTile("@the_vault:vault_lootables", themeName, themeCategory);
            this.templateStackSpawner("ispawner:spawner", themeName, themeCategory);
            return this;
        }

        @Override
        public PaletteDefinition build() {
            for(ThemeBlockType themeBlockType : ThemeBlockType.values()) {
                if(themeBlocks.containsKey(themeBlockType)) {
                    this.weightedTarget(themeBlockType.toString(), weightedBuilder -> {
                        themeBlocks.get(themeBlockType).forEach(weightedBuilder::add);
                    });
                }
                else {
                    if(themeBlockType.altId == null) {
                        continue;
                    }

                    this.weightedTarget(themeBlockType.toString(), weightedBuilder -> {
                        weightedBuilder.add(themeBlockType.altId, 1);
                    });
                }
            }
            return super.build();
        }

        public record ReplacementBlock(ResourceLocation block, int weight) { }

        public enum ThemeBlockType {
            WALL_MAIN("minecraft:stone"),
            WALL_SECONDARY("minecraft:cobblestone"),
            WALL_TERTIARY("minecraft:gray_concrete"),
            WALL_FLOURISH("minecraft:purple_wool"),

            POI_BARS("minecraft:iron_bars"),
            POI_MAIN("minecraft:stone_bricks"),
            POI_MAIN_ALT("minecraft:mossy_stone_bricks"),
            POI_MAIN_ALT_SECONDARY("minecraft:mossy_cobblestone"),
            POI_ACCENT("minecraft:chiseled_stone_bricks"),
            POI_PILLAR("create:andesite_pillar"),
            POI_STAIRS("minecraft:stone_bricks_stairs"),
            POI_STAIRS_SECONDARY("minecraft:cobblestone_stairs"),
            POI_WALL("minecraft:stone_brick_wall"),
            POI_WALL_SECONDARY("minecraft:cobblestone_wall"),
            POI_SLAB("minecraft:stone_brick_slab"),
            POI_SLAB_TERTIARY("minecraft:stone_slab"),
            POI_LOG("minecraft:oak_log"),
            POI_WOOD("minecraft:oak_wood"),
            POI_LEAVES("minecraft:oak_leaves"),
            POI_PLANKS("minecraft:oak_planks"),
            POI_FENCE("minecraft:oak_fence"),
            POI_TRAPDOOR("minecraft:oak_trapdoor"),
            POI_STAIRS_WOOD("minecraft:oak_stairs"),
            POI_POT("minecraft:potted_oak_sapling"),
            POI_LANTERN("minecraft:lantern"),
            POI_CAMPFIRE("minecraft:campfire"),
            POI_BOOKSHELF("minecraft:bookshelf"),
            POI_FENCE_GATE("minecraft:oak_fence_gate"),
            POI_WALL_LANTERN("supplementaries:wall_lantern{Lantern:{Name:\\\"minecraft:lantern\\\"}}\""),
            POI_DOOR("minecraft:oak_door"),
            POI_VERTICAL_SLAB_BRICK("quark:stone_brick_vertical_slab"),
            POI_VERTICAL_SLAB("quark:stone_vertical_slab"),
            POI_SUPPORT("decorative_blocks:oak_support"),

            FLOOR("minecraft:dirt"),
            FLOOR_SLAB("minecraft:spruce_slab"),
            FLOOR_SECONDARY("minecraft:coarse_dirt"),
            FLOOR_TERTIRARY("minecraft:grass_block"),
            FLOOR_CARPET("minecraft:moss_carpet"),
            FLOOR_DECORATION("minecraft:grass"),
            FLOOR_DECORATION_SECONDARY("minecraft:fern"),
            FLOOR_TALL_DECORATION_LOWER("minecraft:tall_grass[half=lower]"),
            FLOOR_TALL_DECORATION_UPPER("minecraft:tall_grass[half=upper]"),
            FLOOR_VINES("minecraft:twisting_vines"),
            FLOOR_PLANT("minecraft:twisting_vines_plant"),

            CEILING_PLANT("minecraft:cave_vines_plant"),
            CEILING_VINES("minecraft:cave_vines"),
            CEILING_HANGING_ACCENT("minecraft:birch_fence"),
            CEILING_DECORATION("minecraft:spore_blossom"),
            CEILING_VARIANT("minecraft:cyan_wool"),
            CEILING_ACCENT("minecraft:pink_wool"),
            CEILING_ACCENT_SECONDARY("minecraft:lime_wool"),
            CEILING_ACCENT_TERTIARY("minecraft:brown_wool"),

            TUNNEL_LANTERN("minecraft:soul_lantern"),
            TUNNEL_PILLAR("quark:azalea_log"),
            TUNNEL_PILLAR_SECONDARY("quark:azalea_planks"),
            TUNNEL_PILLAR_ACCENT("create:cut_veridium_wall"),
            TUNNEL_PILLAR_STAIRS("quark:azalea_planks_stairs"),
            TUNNEL_SLAB("minecraft:cobblestone_slab"),

            TUNNEL_VARIANT_PILLAR("ecologics:azalea_log"),
            TUNNEL_VARIANT_PILLAR_ACCENT("create:cut_crimsite_wall"),



            GOD_ALTAR_ACCENT("minecraft:end_stone"),
            GOD_ALTAR_MAIN("minecraft:end_stone_bricks"),
            GOD_ALTAR_STAIRS("minecraft:end_stone_bricks_stairs"),
            GOD_ALTAR_SLAB("minecraft:end_stone_brick_slab"),

            POST_FENCE("minecraft:warped_fence"),
            POST_BLOCK("minecraft:warped_wart_block"),
            POST_LIGHT("minecraft:shroomlight"),

            POST_VARIANT("architects_palette:twisted_fence"),

            BRIDGE_SLAB("minecraft:oak_slab"),

            DECORATION_BRAZIER("decorative_blocks:brazier"),


            LAVA("minecraft:lava"),
            WATER("minecraft:water"),

            STARTING_ROOM_POOL_TOP_LAYER("minecraft:cyan_stained_glass", "minecraft:water"),
            STARTING_ROOM_POOL_BOTTOM_LAYER("minecraft:light_blue_stained_glass", "minecraft:water"),

            MAGMA("minecraft:magma_block"),

            LIGHT("minecraft:glass", "minecraft:light[level=15]"),


            //Misc
            CANDLE("minecraft:candle"),
            CHAIN("minecraft:chain"),
            BIG_CHAIN("decorative_blocks:chain"),
            COBWEB("minecraft:cobweb"),
            RAIL("minecraft:rail"),
            GRINDSTONE("minecraft:grindstone"),
            SAND("minecraft:sand"),

            METAL_STAIRS("architects_palette:plating_stairs"),
            METAL_PIPE("architects_palette:pipe"),
            METAL_PILLAR("architects_palette:flint_pillar"),
            METAL_TILES("architects_palette:flint_tiles"),


            //Unknown purpose
            NATURE("minecraft:green_concrete"),
            FENCE_WOOD_SECONDARY("ecologics:azalea_fence"),
            HEDGE("quark:oak_hedge");

            private final String id;
            private String altId;

            ThemeBlockType(String s) {
                this.id = s;
                this.altId = null;
            }

            ThemeBlockType(String s, String alt) {
                this.id = s;
                this.altId = alt;
            }

            @Override
            public String toString() {
                return this.id;
            }
        }

        public enum Placeholder {
            ORE_PLACEHOLDER_VOID(VaultMod.id("generic/ore_placeholder_void")),
            TREASURE_DOOR(VaultMod.id("generic/treasure_door_placeholder")),
            COMMON_ELITE_SPAWNERS(VaultMod.id("generic/common_elite_spawners")),
            ROOM_BASE(VaultMod.id("generic/room_base"));

            private final ResourceLocation id;

            Placeholder(ResourceLocation placeholderId) {
                this.id = placeholderId;
            }

            public ResourceLocation getId() {
                return id;
            }

        }
    }

}
