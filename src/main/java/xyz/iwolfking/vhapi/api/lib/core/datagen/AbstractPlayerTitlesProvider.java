package xyz.iwolfking.vhapi.api.lib.core.datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPlayerTitlesProvider implements DataProvider {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    protected final DataGenerator generator;
    protected final String modId;

    private final List<JsonObject> prefixes = new ArrayList<>();
    private final List<JsonObject> suffixes = new ArrayList<>();

    public AbstractPlayerTitlesProvider(DataGenerator generator, String modId) {
        this.generator = generator;
        this.modId = modId;
    }

    protected abstract void buildModifiers();

    protected void addPrefix(String id, String display, String color, int cost) {
        JsonObject obj = new JsonObject();
        obj.addProperty("id", id);
        obj.addProperty("display", display);
        obj.addProperty("color", color);
        obj.addProperty("cost", cost);
        prefixes.add(obj);
    }

    protected void addSuffix(String id, String display, String color, int cost) {
        JsonObject obj = new JsonObject();
        obj.addProperty("id", id);
        obj.addProperty("display", display);
        obj.addProperty("color", color);
        obj.addProperty("cost", cost);
        suffixes.add(obj);
    }

    @Override
    public void run(HashCache cache) throws IOException {
        buildModifiers(); // collect all entries

        JsonObject root = new JsonObject();

        JsonArray prefixArr = new JsonArray();
        prefixes.forEach(prefixArr::add);
        root.add("PREFIXES", prefixArr);

        JsonArray suffixArr = new JsonArray();
        suffixes.forEach(suffixArr::add);
        root.add("SUFFIXES", suffixArr);

        Path path = generator.getOutputFolder()
                .resolve("data/" + modId + "/vault_configs/player_titles/titles.json");

        DataProvider.save(GSON, cache, root, path);
    }

    @Override
    public String getName() {
        return modId + " Player Titles Data";
    }
}
