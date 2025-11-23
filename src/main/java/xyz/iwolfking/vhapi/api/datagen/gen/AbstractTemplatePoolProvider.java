package xyz.iwolfking.vhapi.api.datagen.gen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import iskallia.vault.VaultMod;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.datagen.lib.gen.template_pools.TemplatePoolProcessors;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractTemplatePoolProvider implements DataProvider {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private final DataGenerator generator;
    private final String modid;
    private final List<TemplatePoolBuilder> pools = new ArrayList<>();

    private static final List<String> COMMON_ROOM_NAMES = List.of("bee", "cliffs", "glowstone_lakes", "lakes", "mushroom_forest", "mustard", "pirate", "rainbow_forest", "ore");
    private static final ResourceLocation OMEGA_ROOMS_POOL = VaultMod.id("vault/rooms/omega_rooms");
    private static final ResourceLocation CHALLENGE_ROOMS_POOL = VaultMod.id("vault/rooms/challenge_rooms");

    private final List<JsonObject> templatePoolRegistryEntries = new ArrayList<>();

    public AbstractTemplatePoolProvider(DataGenerator generator, String modid) {
        this.generator = generator;
        this.modid = modid;
    }

    protected abstract void registerPools();

    protected void addPool(Consumer<TemplatePoolBuilder> consumer) {
        TemplatePoolBuilder builder = new TemplatePoolBuilder();
        consumer.accept(builder);
        pools.add(builder);
    }

    protected void addCommonRoomsPool(ResourceLocation id, ResourceLocation themePaletteId, ResourceLocation orePaletteId, int commonRoomWeight, int oreRoomWeight) {
        TemplatePoolBuilder builder = new TemplatePoolBuilder();
        builder.id(id);
        for(String roomName : COMMON_ROOM_NAMES) {
            for(int i = 1; i < 5; i++) {
                int weight = roomName.equals("ore") ? oreRoomWeight : commonRoomWeight;
                builder.addEntry(weight, VaultMod.id("vault/rooms/common/" + roomName + i), paletteConsumer -> {
                    paletteConsumer.add(themePaletteId);
                    if(roomName.equals("ore")) {
                        paletteConsumer.add(orePaletteId);
                    }
                });
            }
        }

        pools.add(builder);
    }

    protected void addCommonRoomsPool(ResourceLocation id, ResourceLocation themePaletteId, ResourceLocation orePaletteId) {
        addCommonRoomsPool(id, themePaletteId, orePaletteId, 1, 1);
    }

    protected void addStandardRoomsPool(ResourceLocation id, ResourceLocation commonRoomsPool) {
        TemplatePoolBuilder builder = new TemplatePoolBuilder();
        builder.id(id);
        builder.addReference(91, commonRoomsPool);
        builder.addReference(7, CHALLENGE_ROOMS_POOL);
        builder.addReference(2, OMEGA_ROOMS_POOL);
        pools.add(builder);
    }

    protected void addStartsPool(ResourceLocation id, ResourceLocation themePaletteId) {
        TemplatePoolBuilder builder = new TemplatePoolBuilder();
        builder.id(id);
        builder.addEntry(1, VaultMod.id("vault/starts/start1"), paletteConsumer -> {
            paletteConsumer.add(themePaletteId);
        });
        pools.add(builder);
    }

    protected void addTunnelsPool(ResourceLocation id, ResourceLocation themePaletteId) {
        TemplatePoolBuilder builder = new TemplatePoolBuilder();
        builder.id(id);
        for(int i = 1; i < 7; i++) {
            builder.addEntry(1, VaultMod.id("vault/tunnels/tunnel" + i), paletteConsumer -> {
                paletteConsumer.add(themePaletteId);
                paletteConsumer.add(VaultMod.id("generic/air_to_cave_air"));
            });
        }

        pools.add(builder);
    }

    protected void createStandardPoolsForTheme(String name, ResourceLocation themePaletteId, ResourceLocation orePaletteId) {
        addCommonRoomsPool(new ResourceLocation(modid, name + "_common_rooms"), themePaletteId, orePaletteId);
        addTunnelsPool(new ResourceLocation(modid, name + "_tunnels"), themePaletteId);
        addStartsPool(new ResourceLocation(modid, name + "_starts"), themePaletteId);
        addStandardRoomsPool(new ResourceLocation(modid, name + "_rooms"), new ResourceLocation(modid, name + "_common_rooms"));
    }

    @Override
    public void run(HashCache cache) throws IOException {
        registerPools();

        for (TemplatePoolBuilder builder : pools) {
            JsonArray array = new JsonArray();
            for (TemplatePoolProcessors.Processor entry : builder.entries) {
                array.add(entry.toJson());
            }
            Path outputPath = generator.getOutputFolder().resolve(
                    "data/" + builder.getId().getNamespace() + "/vault_configs/gen/template_pools/" + builder.getId().getPath() + ".json"
            );

            Files.createDirectories(outputPath.getParent());
            DataProvider.save(GSON, cache, array, outputPath);

            JsonObject item = new JsonObject();

            item.addProperty("id", builder.getId().toString());
            item.addProperty("name", formatReadableName(builder.getId()));
            item.addProperty("1.0", builder.getId().toString());

            templatePoolRegistryEntries.add(item);
        }

        Path keyPath = generator.getOutputFolder().resolve(
                "data/" + modid + "/vault_configs/template_pools/template_pools.json"
        );

        JsonObject keyFile = new JsonObject();
        JsonArray keys = new JsonArray();

        for(JsonObject entry : templatePoolRegistryEntries) {
            keys.add(entry);
        }

        keyFile.add("keys", keys);

        DataProvider.save(GSON, cache, keyFile, keyPath);
    }

    @Override
    public String getName() {
        return modid + " Template Pool Provider";
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

    public static class TemplatePoolBuilder {

        private ResourceLocation id;
        private final List<TemplatePoolProcessors.Processor> entries = new ArrayList<>();

        public TemplatePoolBuilder id(ResourceLocation id) {
            this.id = id;
            return this;
        }

        public ResourceLocation getId() {
            return id;
        }

        public TemplatePoolBuilder addEntry(int weight, ResourceLocation template, Consumer<TemplatePoolProcessors.PaletteConsumer> palettes) {
            entries.add(new TemplatePoolProcessors.TemplateProcessor(template.toString(), weight, palettes));
            return this;
        }

        public TemplatePoolBuilder addReference(int weight, ResourceLocation template) {
            entries.add(new TemplatePoolProcessors.ReferenceProcessor(template.toString(), weight));
            return this;
        }

        public TemplatePoolBuilder addPool(int weight, Consumer<TemplatePoolProcessors.ProcessorConsumer> processorConsumer) {
            entries.add(new TemplatePoolProcessors.PoolProcessor(weight, processorConsumer));
            return this;
        }
    }
}
